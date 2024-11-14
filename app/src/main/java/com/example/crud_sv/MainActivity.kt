package com.example.crud_sv

import android.app.AlertDialog
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    lateinit var btn_add_new: Button

    val students = mutableListOf(
        StudentModel("Nguyễn Văn An", "SV001"),
        StudentModel("Trần Thị Bảo", "SV002"),
        StudentModel("Lê Hoàng Cường", "SV003"),
        StudentModel("Phạm Thị Dung", "SV004"),
        StudentModel("Đỗ Minh Đức", "SV005"),
        StudentModel("Vũ Thị Hoa", "SV006"),
        StudentModel("Hoàng Văn Hải", "SV007"),
        StudentModel("Bùi Thị Hạnh", "SV008"),
        StudentModel("Đinh Văn Hùng", "SV009"),
        StudentModel("Nguyễn Thị Linh", "SV010"),
        StudentModel("Phạm Văn Long", "SV011"),
        StudentModel("Trần Thị Mai", "SV012"),
        StudentModel("Lê Thị Ngọc", "SV013"),
        StudentModel("Vũ Văn Nam", "SV014"),
        StudentModel("Hoàng Thị Phương", "SV015"),
        StudentModel("Đỗ Văn Quân", "SV016"),
        StudentModel("Nguyễn Thị Thu", "SV017"),
        StudentModel("Trần Văn Tài", "SV018"),
        StudentModel("Phạm Thị Tuyết", "SV019"),
        StudentModel("Lê Văn Vũ", "SV020")
    )
    val studentAdapter = StudentAdapter(
        students,
        onEdit = { position ->
            onEditStudent(position)
        },
        onDelete = { position ->
            onDeleteStudent(position)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_add_new = findViewById(R.id.btn_add_new)
        btn_add_new.setOnClickListener {
            showAddStudentDialog()
        }

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun showAddStudentDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null)
        val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
        val editId = dialogView.findViewById<EditText>(R.id.edit_student_id)

        AlertDialog.Builder(this).apply {
            setTitle("Add New Student")
            setView(dialogView)
            setPositiveButton("Add") { _, _ ->
                val name = editName.text.toString()
                val id = editId.text.toString()
                if (name.isNotBlank() && id.isNotBlank()) {
                    students.add(StudentModel(name, id))
                    studentAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Please enter all fields", Toast.LENGTH_SHORT).show()
                }
            }
            setNegativeButton("Cancel", null)
        }.show()
    }

    private fun onEditStudent(position: Int) {
        val student = students[position]
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_student, null)
        val editName = dialogView.findViewById<EditText>(R.id.edit_student_name)
        val editId = dialogView.findViewById<EditText>(R.id.edit_student_id)

        editName.setText(student.studentName)
        editId.setText(student.studentId)

        AlertDialog.Builder(this).apply {
            setTitle("Edit Student")
            setView(dialogView)
            setPositiveButton("Save") { _, _ ->
                val newName = editName.text.toString()
                val newStudentId = editId.text.toString()
                if (newName.isNotBlank() && newStudentId.isNotBlank()) {
                    students[position] = StudentModel(newName, newStudentId)
                    studentAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@MainActivity, "Please enter all fields", Toast.LENGTH_SHORT).show()
                }
            }
            setNegativeButton("Cancel", null)
        }.show()
    }

    private fun onDeleteStudent(position: Int) {
        val student = students[position]

        AlertDialog.Builder(this).apply {
            setTitle("Delete Student")
            setMessage("Are you sure you want to delete ${student.studentName}?")
            setPositiveButton("Delete") { _, _ ->
                // Lưu sinh viên bị xóa để dùng trong Undo
                val removedStudent = students[position]
                students.removeAt(position)
                studentAdapter.notifyDataSetChanged()

                // Hiển thị Snackbar với tùy chọn Undo
                Snackbar.make(findViewById(R.id.recycler_view_students), "${student.studentName} deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        students.add(position, removedStudent)
                        studentAdapter.notifyDataSetChanged()
                    }.show()
            }
            setNegativeButton("Cancel", null)
        }.show()
    }
}
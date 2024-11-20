package com.example.crud_sv

import StudentAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listViewStudents: ListView
    private lateinit var studentAdapter: StudentAdapter

    private val students = mutableListOf(
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


    private val addStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val name = data?.getStringExtra("studentName") ?: return@registerForActivityResult
            val id = data.getStringExtra("studentId") ?: return@registerForActivityResult
            students.add(StudentModel(name, id))
            studentAdapter.notifyDataSetChanged()
            Toast.makeText(this, "Student added successfully", Toast.LENGTH_SHORT).show()
        }
    }

    private val editStudentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val position = data?.getIntExtra("position", -1) ?: return@registerForActivityResult
            val name = data.getStringExtra("studentName") ?: return@registerForActivityResult
            val id = data.getStringExtra("studentId") ?: return@registerForActivityResult
            if (position != -1) {
                students[position] = StudentModel(name, id)
                studentAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Student edited successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewStudents = findViewById(R.id.list_view_students)
        studentAdapter = StudentAdapter(this, students)
        listViewStudents.adapter = studentAdapter

        // Đăng ký ContextMenu
        registerForContextMenu(listViewStudents)

        // Click vào item sẽ hiển thị thông báo
        listViewStudents.setOnItemClickListener { _, _, position, _ ->
            val student = students[position]
            Toast.makeText(this, "${student.studentName} (${student.studentId})", Toast.LENGTH_SHORT).show()
        }
    }

    // Tạo OptionMenu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Xử lý lựa chọn trong OptionMenu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_new -> { 
                val intent = Intent(this, AddStudentActivity::class.java)
                addStudentLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Tạo ContextMenu
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    // Xử lý lựa chọn trong ContextMenu
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        return when (item.itemId) {
            R.id.menu_edit -> {
                val intent = Intent(this, EditStudentActivity::class.java)
                intent.putExtra("position", position)
                intent.putExtra("studentName", students[position].studentName)
                intent.putExtra("studentId", students[position].studentId)
                editStudentLauncher.launch(intent)  // Sử dụng ActivityResultLauncher thay vì startActivityForResult
                true
            }
            R.id.menu_remove -> {
                students.removeAt(position)
                studentAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Student removed", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}

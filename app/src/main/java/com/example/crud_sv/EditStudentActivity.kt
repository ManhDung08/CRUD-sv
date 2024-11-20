package com.example.crud_sv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {

    private lateinit var etStudentName: EditText
    private lateinit var etStudentId: EditText
    private lateinit var btnSave: Button
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        etStudentName = findViewById(R.id.etStudentName)
        etStudentId = findViewById(R.id.etStudentId)
        btnSave = findViewById(R.id.btnSave)

        // Nhận dữ liệu từ Intent
        val studentName = intent.getStringExtra("studentName") ?: ""
        val studentId = intent.getStringExtra("studentId") ?: ""
        position = intent.getIntExtra("position", -1)

        // Điền dữ liệu vào các trường nhập liệu
        etStudentName.setText(studentName)
        etStudentId.setText(studentId)

        // Lắng nghe sự kiện nhấn nút Save
        btnSave.setOnClickListener {
            val updatedName = etStudentName.text.toString().trim()
            val updatedId = etStudentId.text.toString().trim()

            // Kiểm tra các trường hợp tên và ID không trống
            if (updatedName.isEmpty() || updatedId.isEmpty()) {
                Toast.makeText(this, "Please enter both name and ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tạo Intent trả kết quả về MainActivity
            val returnIntent = Intent().apply {
                putExtra("position", position)
                putExtra("studentName", updatedName)
                putExtra("studentId", updatedId)
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()  // Kết thúc activity và trả kết quả về MainActivity
        }
    }
}

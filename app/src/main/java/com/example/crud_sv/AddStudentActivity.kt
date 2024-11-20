package com.example.crud_sv

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    private lateinit var etStudentName: EditText
    private lateinit var etStudentId: EditText
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        etStudentName = findViewById(R.id.etStudentName)
        etStudentId = findViewById(R.id.etStudentId)
        btnSave = findViewById(R.id.btnSave)

        // Lắng nghe sự kiện nhấn nút Save
        btnSave.setOnClickListener {
            val studentName = etStudentName.text.toString().trim()
            val studentId = etStudentId.text.toString().trim()

            // Kiểm tra các trường hợp tên và ID không trống
            if (studentName.isEmpty() || studentId.isEmpty()) {
                Toast.makeText(this, "Please enter both name and ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Tạo Intent trả kết quả về MainActivity
            val returnIntent = Intent().apply {
                putExtra("studentName", studentName)
                putExtra("studentId", studentId)
            }
            setResult(Activity.RESULT_OK, returnIntent)
            finish()  // Kết thúc activity và trả kết quả về MainActivity
        }
    }
}

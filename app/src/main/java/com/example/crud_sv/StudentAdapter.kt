import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.crud_sv.R
import com.example.crud_sv.StudentModel

class StudentAdapter(
    private val context: Context,
    private val students: MutableList<StudentModel>
) : BaseAdapter() {

    override fun getCount(): Int = students.size

    override fun getItem(position: Int): Any = students[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.layout_student_item, parent, false)

        val student = students[position]
        val textName = view.findViewById<TextView>(R.id.text_student_name)
        val textId = view.findViewById<TextView>(R.id.text_student_id)

        textName.text = student.studentName
        textId.text = student.studentId

        return view
    }
}

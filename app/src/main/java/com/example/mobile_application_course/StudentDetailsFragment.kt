package com.example.mobile_application_course

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.mobile_application_course.model.Model
import com.example.mobile_application_course.model.Student
import com.example.mobile_application_course.utils.DateTimeUtils

class StudentDetailsFragment : Fragment() {

    private var student: Student? = null
    private var currentPosition: Int = 0

    private var nameTextView: TextView? = null
    private var idTextView: TextView? = null
    private var phoneTextView: TextView? = null
    private var addressTextView: TextView? = null
    private var birthDateTextView: TextView? = null
    private var birthTimeTextView: TextView? = null
    private var checkBox: CheckBox? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        currentPosition = arguments?.let {
            StudentDetailsFragmentArgs.fromBundle(it).position
        } ?: 0
    }

    override fun onResume() {
        super.onResume()
        student?.let {
            checkBox?.isChecked = it.isChecked
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_student_details, container, false)

        setUp(view)

        return view
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.editStudentFragment -> {
                val action = StudentDetailsFragmentDirections
                    .actionStudentDetailsFragmentToEditStudentFragment(currentPosition)
                findNavController().navigate(action)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setUp(view: View) {
        nameTextView = view.findViewById(R.id.student_details_fragment_name_text_view)
        idTextView = view.findViewById(R.id.student_details_fragment_id_text_view)
        phoneTextView = view.findViewById(R.id.student_details_fragment_phone_text_view)
        addressTextView = view.findViewById(R.id.student_details_fragment_address_text_view)
        birthDateTextView = view.findViewById(R.id.student_details_fragment_birth_date_text_view)
        birthTimeTextView = view.findViewById(R.id.student_details_fragment_birth_time_text_view)
        checkBox = view.findViewById(R.id.student_details_fragment_check_box)

        student = Model.shared.getStudentInPosition(currentPosition)

        student?.let {
            nameTextView?.text = it.name
            idTextView?.text = it.id
            phoneTextView?.text = it.phone
            addressTextView?.text = it.address
            checkBox?.isChecked = it.isChecked
            birthDateTextView?.text = it.birthDate?.let { birthDate ->
                DateTimeUtils.formatDate(birthDate)
            }
            birthTimeTextView?.text = it.birthTime?.let { birthTime ->
                DateTimeUtils.formatTime(birthTime)
            }
        }
    }
}
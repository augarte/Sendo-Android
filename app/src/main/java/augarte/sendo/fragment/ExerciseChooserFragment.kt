package augarte.sendo.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.adapter.CreateWorkoutAdapter
import augarte.sendo.adapter.ExerciseChooseAdapter
import augarte.sendo.dataModel.Exercise
import augarte.sendo.database.SelectTransactions
import kotlinx.android.synthetic.main.bottomsheet_choose_exercise.*

class ExerciseChooserFragment(private val title: String, private var selectedExercises: ArrayList<Exercise>, private val exerciseSelectedListener: CreateWorkoutAdapter.OnExerciseSelectedListener) : Fragment() {

    private lateinit var exerciseChooseAdapter: ExerciseChooseAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottomsheet_choose_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        day_title.setText(title)

        val exerciseList = MainActivity.dbHandler.getExercise(SelectTransactions.SELECT_ALL_EXERCISE_ORDER_NAME, null)

        for (e in exerciseList) {
            if(selectedExercises.any {x-> x.id == e.id }) e.selected = true
        }

        exerciseChooseAdapter = ExerciseChooseAdapter(exerciseList, exerciseSelectedListener, context!!)
        exercise_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = exerciseChooseAdapter
        }

        day_title.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                exerciseSelectedListener.onTitleChanged(p0.toString())
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val filterText = newText.toLowerCase()
                val newList = ArrayList<Exercise>()
                for (exercise in exerciseList) {
                    if (exercise.name?.toLowerCase()?.contains(filterText) == true || exercise.type?.name?.toLowerCase()?.contains(filterText) == true) {
                        newList.add(exercise)
                    }
                }
                exerciseChooseAdapter.setFilter(newList)
                return true
            }
        })
    }
}
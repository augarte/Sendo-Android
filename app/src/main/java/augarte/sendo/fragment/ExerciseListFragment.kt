package augarte.sendo.fragment

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.adapter.ExerciseArchivedAdapter
import augarte.sendo.adapter.ExerciseCategoryAdapter
import augarte.sendo.adapter.ExerciseListAdapter
import augarte.sendo.dataModel.Exercise
import augarte.sendo.database.SelectTransactions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.simplecityapps.recyclerview_fastscroll.utils.Utils
import kotlinx.android.synthetic.main.fragment_exercise_list.*
import kotlinx.android.synthetic.main.fragment_exercise_list.fab_add

class ExerciseListFragment : Fragment() {

    private lateinit var swipeBackground: ColorDrawable
    private lateinit var deleteIcon : Drawable
    private lateinit var exerciseList : MutableList<Exercise>
    private lateinit var exerciseAdapter: Any
    private lateinit var itemTouchHelperCallback: ItemTouchHelper.SimpleCallback

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_exercise_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exerciseList = MainActivity.dbHandler!!.getExercise(SelectTransactions.SELECT_ALL_EXERCISE_ORDER_NAME, null).toMutableList()

        exerciseAdapter = ExerciseListAdapter(exerciseList)
        exercise_list.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = exerciseAdapter as ExerciseListAdapter
        }

        val listener = object: OnDialogClickListener {
            override fun onDialogAccept(dialog: DialogFragment) {
                (exerciseAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>).notifyDataSetChanged()
                dialog.dismiss()
            }
            override fun onDialogDismiss() {
                fab_add.animate().rotation(if (fab_add.rotation==0f) fab_add.rotation+45 else fab_add.rotation-45).start()
            }
        }

        val fab : FloatingActionButton = view.findViewById(R.id.fab_add)
        fab.setOnClickListener {
            val addExerciseDialog = AddExerciseDialogFragment(listener)
            addExerciseDialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
            if(!addExerciseDialog.isAdded){
                addExerciseDialog.show(fragmentManager!!, addExerciseDialog.tag)
                fab_add.animate().rotation(if (fab_add.rotation==0f) fab_add.rotation+45 else fab_add.rotation-45).start()
            }
        }

        configureSwipeDelete()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.option_menu, menu)
        menu.findItem(R.id.group)?.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.list -> {
                exerciseList = MainActivity.dbHandler!!.getExercise(SelectTransactions.SELECT_ALL_EXERCISE_ORDER_NAME, null).toMutableList()
                exercise_list.setPopupTextSize(Utils.toScreenPixels(resources, 44f))
                exerciseAdapter = ExerciseListAdapter(exerciseList)
                exercise_list.adapter = exerciseAdapter as ExerciseListAdapter
            }
            R.id.category -> {
                exerciseList = MainActivity.dbHandler!!.getExercise(SelectTransactions.SELECT_ALL_EXERCISE_ORDER_TYPE, null).toMutableList()
                exercise_list.setPopupTextSize(Utils.toScreenPixels(resources, 20f))
                exerciseAdapter = ExerciseCategoryAdapter(exerciseList)
                exercise_list.adapter = exerciseAdapter as ExerciseCategoryAdapter
            }
            R.id.archived -> {
                exerciseList = MainActivity.dbHandler!!.getExercise(SelectTransactions.SELECT_ARCHIVED_EXERCISES_ORDER_NAME, null).toMutableList()
                exercise_list.setPopupTextSize(Utils.toScreenPixels(resources, 44f))
                exerciseAdapter = ExerciseArchivedAdapter(exerciseList)
                exercise_list.adapter = exerciseAdapter as ExerciseArchivedAdapter
            }
            else -> return super.onOptionsItemSelected(item)

        }
        configureSwipeDelete()
        return true
    }

    private fun configureSwipeDelete(){
        if (exerciseAdapter is ExerciseArchivedAdapter) {
            swipeBackground = ColorDrawable(ResourcesCompat.getColor(resources, R.color.ExerciseUnarchiveBackground, null))
            deleteIcon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_unarchive) }!!
        } else{
            swipeBackground = ColorDrawable(ResourcesCompat.getColor(resources, R.color.ExerciseArchiveBackground, null))
            deleteIcon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_archive) }!!
        }
        deleteIcon.setTint(ResourcesCompat.getColor(resources, R.color.ExerciseArchiveIcon, null))

        itemTouchHelperCallback = object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, position: Int) {
                Handler().postDelayed({
                    when (exerciseAdapter) {
                        is ExerciseListAdapter -> (exerciseAdapter as ExerciseListAdapter).removeWithSwipe(viewHolder)
                        is ExerciseCategoryAdapter -> (exerciseAdapter as ExerciseCategoryAdapter).removeWithSwipe(viewHolder)
                        is ExerciseArchivedAdapter -> (exerciseAdapter as ExerciseArchivedAdapter).removeWithSwipe(viewHolder)
                    }
                }, 200)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView
                val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
                if (dX < 0) {
                    itemView.background = ContextCompat.getDrawable(context!!, R.drawable.background_exercise_swipe)
                    swipeBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    deleteIcon.setBounds(itemView.right - iconMargin - deleteIcon.intrinsicWidth, itemView.top + iconMargin, itemView.right - iconMargin, itemView.bottom - iconMargin)
                }
                else {
                    itemView.background = ContextCompat.getDrawable(context!!, R.drawable.background_exercise_noswipe)
                }
                swipeBackground.draw(c)
                c.save()

                if(dX < 0){
                    c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                }
                deleteIcon.draw(c)
                c.restore()

                //if(-dX < itemView.height+(iconMargin/2)){
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                //}
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(exercise_list)
    }

    interface OnDialogClickListener {
        fun onDialogAccept(dialog: DialogFragment)
        fun onDialogDismiss()
    }
}
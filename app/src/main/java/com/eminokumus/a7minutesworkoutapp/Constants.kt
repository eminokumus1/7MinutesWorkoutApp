package com.eminokumus.a7minutesworkoutapp

import com.eminokumus.a7minutesworkoutapp.vo.Exercise

object Constants {

    fun getExerciseList(): ArrayList<Exercise> {
        val exerciseList = ArrayList<Exercise>()
        val jumpingJacks =
            Exercise(1, "Jumping Jacks", R.drawable.ic_jumping_jacks)
        exerciseList.add(jumpingJacks)

        val wallSit = Exercise(2, "Wall Sit", R.drawable.ic_wall_sit)
        exerciseList.add(wallSit)

        val pushUp = Exercise(3, "Push Up", R.drawable.ic_push_up)
        exerciseList.add(pushUp)

        val abdominalCrunch =
            Exercise(4, "Abdominal Crunch", R.drawable.ic_abdominal_crunch)
        exerciseList.add(abdominalCrunch)

        val stepUpOnChair =
            Exercise(
                5,
                "Step-Up onto Chair",
                R.drawable.ic_step_up_onto_chair,
            )
        exerciseList.add(stepUpOnChair)

        val squat = Exercise(6, "Squat", R.drawable.ic_squat)
        exerciseList.add(squat)

        val tricepDipOnChair =
            Exercise(
                7,
                "Tricep Dip On Chair",
                R.drawable.ic_triceps_dip_on_chair
            )
        exerciseList.add(tricepDipOnChair)

        val plank = Exercise(8, "Plank", R.drawable.ic_plank)
        exerciseList.add(plank)

        val highKneesRunningInPlace =
            Exercise(
                9, "High Knees Running In Place",
                R.drawable.ic_high_knees_running_in_place
            )
        exerciseList.add(highKneesRunningInPlace)

        val lunges = Exercise(10, "Lunges", R.drawable.ic_lunge)
        exerciseList.add(lunges)

        val pushupAndRotation =
            Exercise(
                11,
                "Push up and Rotation",
                R.drawable.ic_push_up_and_rotation,
            )
        exerciseList.add(pushupAndRotation)

        val sidePlank = Exercise(12, "Side Plank", R.drawable.ic_side_plank)
        exerciseList.add(sidePlank)

        return exerciseList
    }
}
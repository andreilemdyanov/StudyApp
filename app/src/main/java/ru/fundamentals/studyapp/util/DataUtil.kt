package ru.fundamentals.studyapp.util

import ru.fundamentals.studyapp.R
import ru.fundamentals.studyapp.data.Actor
import ru.fundamentals.studyapp.data.Movie

class DataUtil {
    companion object {
        fun generateMovies(): List<Movie> {
            val list = mutableListOf<Movie>()
            list.add(
                Movie(
                    R.drawable.avengers,
                    "13+",
                    false,
                    "Action, Adventure, Drama",
                    "125 REVIEWS",
                    4,
                    "Avengers: End Game",
                    "137 MIN",
                    "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos\\' actions and restore balance to the universe.",
                    listOf(
                        Actor("Robert Downey Jr.", R.drawable.robert_downey),
                        Actor("Chris Evans", R.drawable.chris_evans),
                        Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                        Actor("Chris Hemsworth", R.drawable.chris_hemsworth),
                        Actor("Thomas Hiddleston", R.drawable.hiddleston),
                        Actor("Samuel L. Jackson", R.drawable.jackson),
                        Actor("Gwyneth Paltrow", R.drawable.paltrow),
                        Actor("Jeremy Renner", R.drawable.renner),
                        Actor("Scarlett Johansson", R.drawable.johansson)
                    )
                )
            )
            list.add(
                Movie(
                    R.drawable.tenet,
                    "16+",
                    false,
                    "Action, Sci-Fi, Thriller",
                    "98 REVIEWS",
                    5,
                    "Tenet",
                    "97 MIN",
                    "An unnamed CIA agent, the \"Protagonist\", participates in an undercover operation at a Kyiv opera house. He is aided by a masked soldier with a distinctive trinket, who appears to \"un-fire\" a bullet through a hostile gunman.",
                    listOf(
                        Actor("Robert Downey Jr.", R.drawable.robert_downey),
                        Actor("Chris Evans", R.drawable.chris_evans),
                        Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                        Actor("Chris Hemsworth", R.drawable.chris_hemsworth),
                        Actor("Thomas Hiddleston", R.drawable.hiddleston),
                        Actor("Samuel L. Jackson", R.drawable.jackson),
                        Actor("Gwyneth Paltrow", R.drawable.paltrow),
                        Actor("Jeremy Renner", R.drawable.renner),
                        Actor("Scarlett Johansson", R.drawable.johansson)
                    )
                )
            )
            list.add(
                Movie(
                    R.drawable.black_widow,
                    "13+",
                    false,
                    "Action, Adventure, Sci-Fi",
                    "38 REVIEWS",
                    4,
                    "Black Widow",
                    "102 MIN",
                    "Following the events of Captain America: Civil War (2016), Natasha Romanoff finds herself alone and forced to confront a dangerous conspiracy with ties to her past. Pursued by a force that will stop at nothing to bring her down, Romanoff must deal with her history as a spy and the broken relationships left in her wake long before she became an Avenger.",
                    listOf(
                        Actor("Robert Downey Jr.", R.drawable.robert_downey),
                        Actor("Chris Evans", R.drawable.chris_evans),
                        Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                        Actor("Chris Hemsworth", R.drawable.chris_hemsworth),
                        Actor("Thomas Hiddleston", R.drawable.hiddleston),
                        Actor("Samuel L. Jackson", R.drawable.jackson),
                        Actor("Gwyneth Paltrow", R.drawable.paltrow),
                        Actor("Jeremy Renner", R.drawable.renner),
                        Actor("Scarlett Johansson", R.drawable.johansson)
                    )
                )
            )
            list.add(
                Movie(
                    R.drawable.wonder_woman,
                    "13+",
                    false,
                    "Action, Adventure, Fantasy",
                    "74 REVIEWS",
                    5,
                    "Wonder Woman 1984",
                    "120 MIN",
                    "Wonder Woman 1984 is scheduled to be theatrically released by Warner Bros. Pictures in a handful of international markets starting on December 16, 2020, and theatrically in the United States and Canada on December 25 in Dolby Cinema and IMAX while streaming on HBO Max the same day.",
                    listOf(
                        Actor("Robert Downey Jr.", R.drawable.robert_downey),
                        Actor("Chris Evans", R.drawable.chris_evans),
                        Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                        Actor("Chris Hemsworth", R.drawable.chris_hemsworth),
                        Actor("Thomas Hiddleston", R.drawable.hiddleston),
                        Actor("Samuel L. Jackson", R.drawable.jackson),
                        Actor("Gwyneth Paltrow", R.drawable.paltrow),
                        Actor("Jeremy Renner", R.drawable.renner),
                        Actor("Scarlett Johansson", R.drawable.johansson)
                    )
                )
            )
            list.add(
                Movie(
                    R.drawable.bird_box,
                    "16+",
                    false,
                    "Horror, Fantastic, Drama",
                    "150 REVIEWS",
                    3,
                    "Bird Box",
                    "124 MIN",
                    "In a post-apocalyptic world, Malorie Hayes advises two young, unnamed children that they will be going downstream on a river in a rowboat.",
                    listOf(
                        Actor("Robert Downey Jr.", R.drawable.robert_downey),
                        Actor("Chris Evans", R.drawable.chris_evans),
                        Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                        Actor("Chris Hemsworth", R.drawable.chris_hemsworth),
                        Actor("Thomas Hiddleston", R.drawable.hiddleston),
                        Actor("Samuel L. Jackson", R.drawable.jackson),
                        Actor("Gwyneth Paltrow", R.drawable.paltrow),
                        Actor("Jeremy Renner", R.drawable.renner),
                        Actor("Scarlett Johansson", R.drawable.johansson)
                    )
                )
            )
            list.add(
                Movie(
                    R.drawable.ford_ferrary,
                    "12+",
                    false,
                    "Drama",
                    "115 REVIEWS",
                    3,
                    "Ford v Ferrary",
                    "152 MIN",
                    "In 1963, Ford Motor Company Vice President Lee Iacocca proposes to Henry Ford II to purchase the cash-strapped Ferrari as a means to boost their car sales by participating in the 24 Hours of Le Mans.",
                    listOf(
                        Actor("Robert Downey Jr.", R.drawable.robert_downey),
                        Actor("Chris Evans", R.drawable.chris_evans),
                        Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                        Actor("Chris Hemsworth", R.drawable.chris_hemsworth),
                        Actor("Thomas Hiddleston", R.drawable.hiddleston),
                        Actor("Samuel L. Jackson", R.drawable.jackson),
                        Actor("Gwyneth Paltrow", R.drawable.paltrow),
                        Actor("Jeremy Renner", R.drawable.renner),
                        Actor("Scarlett Johansson", R.drawable.johansson)
                    )
                )
            )
            list.add(
                Movie(
                    R.drawable.avatar,
                    "12+",
                    false,
                    "Fantastic, Action, Drama",
                    "115 REVIEWS",
                    5,
                    "Avatar",
                    "162 MIN",
                    "In 2154, humans have depleted Earth's natural resources, leading to a severe energy crisis. The Resources Development Administration (RDA) mines a valuable mineral unobtanium on Pandora, a densely forested habitable moon orbiting Polyphemus, a fictional gas giant in the Alpha Centauri star system.",
                    listOf(
                        Actor("Robert Downey Jr.", R.drawable.robert_downey),
                        Actor("Chris Evans", R.drawable.chris_evans),
                        Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                        Actor("Chris Hemsworth", R.drawable.chris_hemsworth),
                        Actor("Thomas Hiddleston", R.drawable.hiddleston),
                        Actor("Samuel L. Jackson", R.drawable.jackson),
                        Actor("Gwyneth Paltrow", R.drawable.paltrow),
                        Actor("Jeremy Renner", R.drawable.renner),
                        Actor("Scarlett Johansson", R.drawable.johansson)
                    )
                )
            )
            list.add(
                Movie(
                    R.drawable.san_andreas,
                    "18+",
                    false,
                    "Thriller, Action, Drama",
                    "15 REVIEWS",
                    4,
                    "San Andreas",
                    "114 MIN",
                    "Caltech seismologist Dr. Lawrence Hayes and his colleague Dr. Kim Park are at Hoover Dam testing a new earthquake predicting model when a nearby and previously unknown fault ruptures, triggering a 7.1 magnitude earthquake that collapses the dam.",
                    listOf(
                        Actor("Robert Downey Jr.", R.drawable.robert_downey),
                        Actor("Chris Evans", R.drawable.chris_evans),
                        Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                        Actor("Chris Hemsworth", R.drawable.chris_hemsworth),
                        Actor("Thomas Hiddleston", R.drawable.hiddleston),
                        Actor("Samuel L. Jackson", R.drawable.jackson),
                        Actor("Gwyneth Paltrow", R.drawable.paltrow),
                        Actor("Jeremy Renner", R.drawable.renner),
                        Actor("Scarlett Johansson", R.drawable.johansson)
                    )
                )
            )
            list.add(
                Movie(
                    R.drawable.wolverine,
                    "16+",
                    false,
                    "Fantastic, Action, Adventure",
                    "97 REVIEWS",
                    4,
                    "Wolverine",
                    "126 MIN",
                    "In August 1945, Logan is held in a Japanese POW camp near Nagasaki. During the atomic bombing of Nagasaki, Logan saves an officer named Ichirō Yashida by shielding him from the blast.",
                    listOf(
                        Actor("Robert Downey Jr.", R.drawable.robert_downey),
                        Actor("Chris Evans", R.drawable.chris_evans),
                        Actor("Mark Ruffalo", R.drawable.mark_ruffalo),
                        Actor("Chris Hemsworth", R.drawable.chris_hemsworth),
                        Actor("Thomas Hiddleston", R.drawable.hiddleston),
                        Actor("Samuel L. Jackson", R.drawable.jackson),
                        Actor("Gwyneth Paltrow", R.drawable.paltrow),
                        Actor("Jeremy Renner", R.drawable.renner),
                        Actor("Scarlett Johansson", R.drawable.johansson)
                    )
                )
            )

            return list
        }
    }
}
package ba.etf.rma22.projekat

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import org.hamcrest.CoreMatchers
import org.hamcrest.Matchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)

class MySpirala2AndroidTest {
    @get:Rule
    val intentsTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testZadatak1() {

        onView(withId(R.id.filterAnketa)).check(matches(isDisplayed()))

        val ankete = AnketaRepository.getMyAnkete()
        onView(withId(R.id.listaAnketa)).check(UtilTestClass.hasItemCount(5))
        for (anketa in ankete) {
            UtilTestClass.itemTest(R.id.listaAnketa, anketa)
        }

        // prelazak na FragmentIstrazivanje
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(1))

        // odabir godine
        onView(withId(R.id.odabirGodina)).perform(click())

        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("2")
            )
        ).perform(ViewActions.click())

        // odabir istrazivanja
        onView(withId(R.id.odabirIstrazivanja)).perform(ViewActions.click())

        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("Istrazivanje 4")
            )
        ).perform(ViewActions.click())

        // odabir grupe
        onView(ViewMatchers.withId(R.id.odabirGrupa)).perform(ViewActions.click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("I4-G3")
            )
        ).perform(ViewActions.click())


        onView(withId(R.id.dodajIstrazivanjeDugme)).perform(ViewActions.click())

        // provjera da li je zamjenjen FragmentIstrazivanje sa FragmentPoruka i da li je poruka pravilno ispisana
        onView(withId(R.id.tvPoruka)).check(matches(isDisplayed()))
        onView(withSubstring("Uspješno ste upisani u grupu I4-G3 istraživanja Istrazivanje 4!")).check(matches(isDisplayed()))


        // povratak na poziciji 0 i da li je dodana anketa
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(0))
        val ankete2 = AnketaRepository.getMyAnkete()
        onView(withId(R.id.listaAnketa)).check(UtilTestClass.hasItemCount(6))
        for (anketa in ankete2) {
            UtilTestClass.itemTest(R.id.listaAnketa, anketa)
        }

        // Provjera da li se na poziciji 1 u viewpager-u nalazi FragmentIstrazivanje
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(1))
        onView(withId(R.id.odabirGodina)).check(matches(isDisplayed()))
        onView(withId(R.id.odabirIstrazivanja)).check(matches(isDisplayed()))
        onView(withId(R.id.odabirGrupa)).check(matches(isDisplayed()))

    }

    @Test
    fun testZadatak2() {

        onView(withId(R.id.filterAnketa)).perform(click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("Urađene ankete")
            )
        ).perform(click())

        // provjera broja urađenih anketa i da li su ispravne
        val ankete = AnketaRepository.getDone()
        onView(withId(R.id.listaAnketa)).check(UtilTestClass.hasItemCount(1))
        for (anketa in ankete) {
            UtilTestClass.itemTest(R.id.listaAnketa, anketa)
        }

        onView(withId(R.id.filterAnketa)).perform(click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("Sve moje ankete")
            )
        ).perform(click())

        onView(withId(R.id.listaAnketa)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                CoreMatchers.allOf(
                    hasDescendant(withText("Anketa 13")),
                    hasDescendant(withText("Istrazivanje 6"))
                ), click()
            )
        )

        onView(withId(R.id.tekstPitanja)).check(matches(isDisplayed()))
        onView(withId(R.id.odgovoriLista)).check(matches(isDisplayed()))
        onView(withId(R.id.dugmeZaustavi)).check(matches(isDisplayed()))

        onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(0).perform(click())
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(1))

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(2))
        onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(2).perform(click())

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(3))

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(4))
        onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(1).perform(click())

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(5))

        //Provjera da li je progres ankete pravilno prikazan
        onView(withSubstring("60%")).check(matches(isDisplayed()))

        // Predaja ankete i provjera da li je poruka pravilno ispisana
        onView(withId(R.id.dugmePredaj)).perform(click())
        onView(withSubstring("Završili ste anketu Anketa 13 u okviru istraživanja Istrazivanje 6")).check(matches(isDisplayed()))

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(0))

        // Provjera da li se na poziciji 1 u viewpager-u nalazi FragmentIstrazivanje
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(1))
        onView(withId(R.id.odabirGodina)).check(matches(isDisplayed()))
        onView(withId(R.id.odabirIstrazivanja)).check(matches(isDisplayed()))
        onView(withId(R.id.odabirGrupa)).check(matches(isDisplayed()))

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(0))

        onView(withId(R.id.filterAnketa)).perform(click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("Urađene ankete")
            )
        ).perform(click())

        // provjera broja urađenih anketa i da li su ispravne, sada bi ih trebalo biti 3, za jednu više
        val ankete2 = AnketaRepository.getDone()
        onView(withId(R.id.listaAnketa)).check(UtilTestClass.hasItemCount(2))
        for (anketa in ankete2) {
            UtilTestClass.itemTest(R.id.listaAnketa, anketa)
        }

        // Odabiremo urađenu anketu
        onView(withId(R.id.listaAnketa)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                CoreMatchers.allOf(
                    hasDescendant(withText("Anketa 5")),
                    hasDescendant(withText("Istrazivanje 3"))
                ), click()
            )
        )

        onView(withId(R.id.tekstPitanja)).check(matches(isDisplayed()))
        onView(withId(R.id.odgovoriLista)).check(matches(isDisplayed()))
        onView(withId(R.id.dugmeZaustavi)).check(matches(isDisplayed()))

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(4))

        // pošto je anketa urađena dugme za predaju ne bi trebalo biti vidljivo
        onView(withId(R.id.dugmePredaj)).check(matches(not(isDisplayed())))

        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(3))
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(2))

        onView(withId(R.id.dugmeZaustavi)).perform(click())

        // Provjera da li se na poziciji 1 u viewpager-u nalazi FragmentIstrazivanje
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(1))
        onView(withId(R.id.odabirGodina)).check(matches(isDisplayed()))
        onView(withId(R.id.odabirIstrazivanja)).check(matches(isDisplayed()))
        onView(withId(R.id.odabirGrupa)).check(matches(isDisplayed()))

        // Provjera da li se na poziciji 0 u viewpager-u nalazi FragmentAnekete
        onView(withId(R.id.pager)).perform(ViewPager2Actions.scrollToPosition(0))
        onView(withId(R.id.filterAnketa)).check(matches(isDisplayed()))


    }

}
package ba.etf.rma22.projekat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    var br:Int=0
    var br2:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.pager)
        val fragments =
            mutableListOf<Fragment>()

        //viewPager.offscreenPageLimit = 2
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragments, lifecycle)
        viewPager.adapter = viewPagerAdapter

        fragments.add(FragmentAnkete.newInstance(viewPagerAdapter))
        fragments.add(FragmentIstrazivanje.newInstance(viewPagerAdapter))


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {


                if(br2==0 && position==1 && fragments.get(1).toString().contains("FragmentPoruka") ){
                    br=1;
                }

                if(position==1 && fragments.get(1).toString().contains("FragmentPoruka") ){
                    br=1;
                }
                else if (position == 0 && fragments.get(1).toString().contains("FragmentPoruka") && br!=0) {

                    viewPagerAdapter.zamijeni(1, FragmentIstrazivanje.newInstance(viewPagerAdapter))
                    br=0;

                }
                else if(position == 0 && fragments.get(1).toString().contains("FragmentPoruka") && br==0){

                    viewPager.setCurrentItem(1)
                    br++;

                }

                super.onPageSelected(position)
            }

        })

    }



}
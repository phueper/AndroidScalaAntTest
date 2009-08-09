package net.hueper.android.test

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.{TextView, ScrollView}
import _root_.android.view.View
import _root_.android.hardware.{Sensor, SensorManager}

class AndroidAntScalaTest extends Activity {
    // converter from View to any view Type (e.g. for findViewById)
	implicit def view2TextView(v:View):TextView = {v.asInstanceOf[TextView] }
    implicit def view2ScrollView(v:View):ScrollView = {v.asInstanceOf[ScrollView] }
    // converter for anonymous func to Runnable (e.g. for post())
    implicit def func2Runnable(func: ()=>Unit):Runnable = {
      new Runnable() {
    	  def run() {
    	    func()
    	  }
      }
       
    }
	
	override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)
		var textView:TextView = findViewById(R.id.TextView01)
		var scrollView:ScrollView = findViewById(R.id.ScrollView01)
		textView.setText("blablub\nblubdiblub")
		for (i <- 1 to 100) {
			textView.append(i + "\n")
			println("log test " + i)
		}
		scrollView.post(()=>{
		  scrollView.fullScroll(View.FOCUS_DOWN)
		  ()
		})
		true
	}

}

package net.hueper.android.test

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget._
import _root_.android.view.View
import _root_.android.view.View.OnClickListener
import _root_.android.widget.AdapterView
import _root_.android.widget.AdapterView.OnItemSelectedListener
import _root_.android.hardware.{Sensor, SensorManager}
import _root_.scala.actors.Actor
import _root_.scala.actors.Actor._

class AndroidAntScalaTest extends Activity {
	// converter from View to any view Type (e.g. for findViewById)
	implicit def viewConverter[T](v:View):T = {v.asInstanceOf[T] }
	// converter for anonymous func to Runnable (e.g. for post())
	implicit def func2Runnable(func: ()=>Unit):Runnable = {
			new Runnable() {
				def run() {
					func()
				}
			}
	}
	// converter for anonymous func to OnClickListener
	implicit def func2OnClickListener(func: (View)=>Unit):OnClickListener = {
			new OnClickListener() {
				def onClick(v:View) = func.apply(v)
			}
	}
	// converter for a tuple of anonymous functions to OnItemSelectedListener
	implicit def func2OnItemSelectedListener(funcs:(
		(AdapterView[_], View, int, long)=>Unit, 
		(AdapterView[_])=>Unit)) = {
			new OnItemSelectedListener() {
				def onItemSelected(p:AdapterView[_], v:View, position:int, id:long) = funcs._1.apply(p, v, position, id)
				def onNothingSelected(p:AdapterView[_]) = funcs._2.apply(p)
			}
	}

	override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)
		val textView:TextView = findViewById(R.id.TextView01)
		val scrollView:ScrollView = findViewById(R.id.ScrollView01)
		val spinner:Spinner = findViewById(R.id.Spinner01)
		val button:Button = findViewById(R.id.Button01)
		val buttonActor:ButtonActor = new ButtonActor
		buttonActor.start

		textView.setText("")
/*
		textView.setText("blablub\nblubdiblub")
		for (i <- 1 to 100) {
			textView.append(i + "\n")
			println("log test " + i)
		}
*/
		val sm:SensorManager = getSystemService(_root_.android.content.Context.SENSOR_SERVICE).asInstanceOf[SensorManager]
		val sensors = sm.getSensorList(Sensor.TYPE_ALL)
		val it = sensors.iterator()
		val sensorNames:java.util.List[String] = new java.util.ArrayList()
		while (it.hasNext) {
			val name = it.next().getName()
			textView.append(name + "\n")
			sensorNames.add(name)
		}
		val sensorAdapter = new ArrayAdapter[String](this, _root_.android.R.layout.simple_spinner_item, sensorNames)
		spinner.setAdapter(sensorAdapter)

		spinner.setOnItemSelectedListener( (p:AdapterView[_], v:View, pos:int, id:long)=>{
			textView.append(spinner.getSelectedItem().toString() + "\n")
			scrollViewUpdate(scrollView)
			},
			(p:AdapterView[_])=>{textView.append("nothing selected\n");scrollViewUpdate(scrollView)})

		button.setOnClickListener((v:View)=>{textView.append("click from thread: " + Thread.currentThread().getId() + "\n");buttonActor!"msg";scrollViewUpdate(scrollView)})
		scrollViewUpdate(scrollView)
		true
	}

	def scrollViewUpdate(scrollView:ScrollView) {
		/* post function for scrollView, after updates, we want to scroll to bottom... */
		scrollView.post(()=>{
			scrollView.fullScroll(View.FOCUS_DOWN)
			() // must return Unit
		})
	}
 
	class ButtonActor extends Actor {
		private val textView:TextView = findViewById(R.id.TextView01)
		private val scrollView:ScrollView = findViewById(R.id.ScrollView01)
		private val spinner:Spinner = findViewById(R.id.Spinner01)
		def act() {
			loop {
				receive {
					case msg => textView.append("msg received: " + msg.toString() + " from thread: " + Thread.currentThread().getId() + "\n");scrollViewUpdate(scrollView); 
				}
			}
		}
	}

}




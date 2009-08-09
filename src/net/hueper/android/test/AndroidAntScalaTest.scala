package net.hueper.android.test

import _root_.android.app.Activity
import _root_.android.os.Bundle
import _root_.android.widget.TextView
import _root_.android.view.View

class AndroidAntScalaTest extends Activity {
	implicit def textViewConverter(v:View):TextView = {v.asInstanceOf[TextView] }
  
	override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.main)
		var mTextView:TextView = findViewById(R.id.TextView01)
		mTextView.setText("blablub\nblubdiblub")
		for (i <- 1 to 100) {
			mTextView.append(i + "\n")
			println("log test" + i)
		}
		true
	}

}

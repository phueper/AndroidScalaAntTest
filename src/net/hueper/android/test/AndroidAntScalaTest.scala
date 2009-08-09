package net.hueper.android.test

import _root_.android.app.Activity
import _root_.android.os.Bundle

class AndroidAntScalaTest extends Activity {
	override def onCreate(savedInstanceState: Bundle) {
		super.onCreate(savedInstanceState)
		// use a bit of Scala's stdlib, just to show off
		setContentView(List(R.layout.main) head)
	}

}

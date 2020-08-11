import $exec.^.nkd

val testScatter = ujson.read(requests.get("https://vega.github.io/vega/examples/brushing-scatter-plots.vg.json").text)

val rawData = ujson.read(requests.get("https://raw.githubusercontent.com/domoritz/maps/master/data/iris.json"))

val autoSize = ujson.read(""" {"autosize": {"type": "fit", "resize": true}}""")
var data = testScatter("data")
data.arr(0) = ujson.Obj("name" -> "iris", "values" -> rawData) 
testScatter("autosize") = autoSize("autosize")

val chartSizeSignal = ujson.read("""    {
      "name": "chartSize",
      "update": "min(width, height) / 4 - chartPad"
    }""")
	
val widthSignal = ujson.read(""" {
      "name": "width",
      "init": "isFinite(containerSize()[0]) ? min(containerSize()[0], containerSize()[1])  : 200",
      "on": [
        {
          "update": "isFinite(containerSize()[0]) ? min(containerSize()[0], containerSize()[1]) : 200",
          "events": "window:resize"
        }
      ]
    }"""
	)
	
val heightSignal = ujson.read("""    {
      "name": "height",
      "init": "isFinite(containerSize()[1]) ? min(containerSize()[0], containerSize()[1]) : 200",
      "on": [
        {
          "update": "isFinite(containerSize()[1]) ? min(containerSize()[0], containerSize()[1]) : 200",
          "events": "window:resize"
        }
      ]
    }	""")
		
val chartSizeSignalIndex = getSignalIndex(testScatter, "chartSize")
val widthSignalIndex = getWidthSignalIndex(testScatter)
val heightSignalIndex = getHeightSignalIndex(testScatter)

testScatter("signals")(chartSizeSignalIndex) = chartSizeSignal
testScatter("signals")(widthSignalIndex) = widthSignal
testScatter("signals")(heightSignalIndex) = heightSignal

//println(ujson.write(testScatter, indent=2))

plot(testScatter, "Scatter plotting")

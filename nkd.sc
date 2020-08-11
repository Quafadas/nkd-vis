import java.io.{ File, PrintWriter }
import java.awt.Desktop

def writeToTempFile(contents: String, 
                    prefix: Option[String] = None,
                    suffix: Option[String] = None): File = {
  val tempFi = File.createTempFile(prefix.getOrElse("prefix-"),
                                   suffix.getOrElse(".html"))
  tempFi.deleteOnExit()
  new PrintWriter(tempFi) {
    try {
      write(contents)
    } finally {
      close()
    }
  }
  tempFi
}

def openBrowserToFile(f: File) = Desktop.getDesktop().browse(f.toURI())

def plot(spec : ujson.Value, title: String = "") = {
		
	val f = writeToTempFile(header(spec, title), None, Some(".html"))
	openBrowserToFile(f)
}

def header(spec : ujson.Value, title: String = "") = {
	
	raw"""<!DOCTYPE html>
	<html>
	<head>
	  <!-- Import Vega & Vega-Lite (does not have to be from CDN) -->
	  <script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
	  <script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
	  <!-- Import vega-embed -->
	  <script src="https://cdn.jsdelivr.net/npm/vega-embed@5"></script>
	  <style>
		div#vis {
			width: 95vw;
			height:90vh;
			style="position: fixed; left: 0; right: 0; top: 0; bottom: 0"
		}	
		div#title {
			width: 100vw;
			height: 5vh;
			text-align: center;
		}
	</style>
	<title>$title</title>
	  
	</head>
	<body>

		<div id="title"><h1>$title</h1></div>
		<div id="vis"></div>

	<script type="text/javascript">
	  const spec = ${ujson.write(spec, indent=2)};  
	  const view = new vega.View(vega.parse(spec), {
		renderer: "canvas", // renderer (canvas or svg)
		container: "#vis", // parent DOM container
		hover: true // enable hover processing
	  });
	  view.runAsync();
	</script>
	</body>
	</html> """
}

def fixAppearence(spec:ujson.Value) = {
	spec("autosize") = autoSize("autosize")
	spec("signals") = spec("signals").arr ++ ujson.read(resizeSignals2).arr
	spec
}


val resizeSignals2 = ujson.read("""

[
{
      "name": "width",
      "init": "isFinite(containerSize()[0]) ? containerSize()[0] : 200",
      "on": [
        {
          "update": "isFinite(containerSize()[0]) ? containerSize()[0] : 200",
          "events": "window:resize"
        }
      ]
    },
    {
      "name": "height",
      "init": "isFinite(containerSize()[1]) ? containerSize()[1] : 200",
      "on": [
        {
          "update": "isFinite(containerSize()[1]) ? containerSize()[1] : 200",
          "events": "window:resize"
        }
      ]
    }
]

""")

def getSignalIndex(spec: ujson.Value, name: String) = spec("signals").arr.indexWhere(_.obj("name").str == name) 

def getWidthSignalIndex(spec: ujson.Value) = getSignalIndex(spec, "width")
def getHeightSignalIndex(spec: ujson.Value) = getSignalIndex(spec, "height")

val autoSize = ujson.read(""" {"autosize": {"type": "fit", "resize": true, "contains": "padding"}}""")

val style =  ujson.read("""{"style": "cell"}""")
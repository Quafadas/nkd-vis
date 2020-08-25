import $exec.^.^.nkdLite

val test = ujson.read(""" 

{
  "$schema": "https://vega.github.io/schema/vega-lite/v4.json",
  "description": "Drag a rectangular brush to show (first 20) selected points in a table.",
  "data": {"url": "https://raw.githubusercontent.com/vega/vega/master/docs/data/cars.json"},
  "width": "container",
  "mark": {"type": "point", "tooltip": {"content": "data"}  },
  "height": "container",
    "encoding": {
    "x": {"field": "Horsepower", "type": "quantitative"},
    "y": {"field": "Miles_per_Gallon", "type": "quantitative"}    
  }
}

 """)

plot(test, "Bar Chart")
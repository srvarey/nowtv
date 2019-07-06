var items = Vector("a", "b", "c")

val newelement = List("a", "d")

for (x <- newelement) if (!items.contains(x)) items = items :+ x

items
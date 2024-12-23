package modelo

class CuentaMesa {
    private val items = mutableListOf<Pair<ItemMenu, Int>>()
    var aceptaPropina: Boolean = false

    fun agregarItem(item: ItemMenu, cantidad: Int) {
        items.removeIf { it.first == item }
        if (cantidad > 0) {
            items.add(Pair(item, cantidad))
        }
    }

    fun calcularTotalSinPropina(): Int {
        return items.sumOf { it.first.precio * it.second }
    }

    fun calcularPropina(): Int {
        return if (aceptaPropina) (calcularTotalSinPropina() * 0.1).toInt() else 0
    }

    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }
}
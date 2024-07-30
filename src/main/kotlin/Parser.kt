class Parser {
    private var string = ""
    private var index = 0

    private var symbol: Char = '0'

    private fun nextSymbol(): Boolean {
        if (index < string.length - 1) {
            this.symbol = string[++index]
            return true
        }
        return false
    }

    fun parse(string: String) {
        if (string.isEmpty())
            return
        this.string = string
        this.index = 0
        this.symbol = string[index]
        s()
    }

    private fun s() {
        t()
        sStar()
    }

    private fun sStar() {
        if (symbol == '+' || symbol == '-') {
            nextSymbol()
            t()
            sStar()
        }
    }

    private fun t() {
        m()
        tStar()
    }

    private fun tStar() {
        if (symbol == '*' || symbol == '/') {
            nextSymbol()
            m()
            tStar()
        }
    }

    private fun m() {
        if (symbol == '(') {
            nextSymbol()
            s()
            if (symbol == ')') {
                nextSymbol()
            } else {
                error("close round bracket")
            }
        } else if (symbol.isLetter()) {
            n()
            if (symbol == '(') {
                nextSymbol()
                if (symbol == '-' || symbol.isDigit()) {
                    z()
                }
                if (symbol.isLetter()) {
                    n()
                }
                if (symbol == ')') {
                    nextSymbol()
                } else {
                    error("close round bracket")
                }
            }
        } else if (symbol == '-' || symbol.isDigit()) {
            z()
        } else {
            error("something went wrong. unknown digit at $index")
        }
    }

    private fun n() {
        if (symbol.isLetter()) {
            nextSymbol()
            nStar()
        } else {
            error("first should be a letter")
        }
    }

    private fun nStar() {
        if (symbol.isLetterOrDigit()) {
            if (nextSymbol()) {
                nStar()
            }
        }
    }

    private fun z() {
        if (symbol == '-' || symbol.isDigit()) {
            nextSymbol()
        }
        zStar()
    }

    private fun zStar() {
        if (symbol.isDigit()) {
            if (nextSymbol()) {
                zStar()
            }
        }
        if (symbol.isLetter()) {
            error("wrong digit")
        }
    }
}
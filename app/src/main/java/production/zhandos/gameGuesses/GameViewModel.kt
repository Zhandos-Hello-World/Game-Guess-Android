package production.zhandos.gameGuesses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private val words = listOf("Android", "Fragment", "Activity")
    private var secretWord = words.random().uppercase()
    private val _secretWordDisplay = MutableLiveData("")
    val secretWordDisplay get() = _secretWordDisplay
    private var correctGuesses = ""
    private val _incorrectGuesses = MutableLiveData("")
    val incorrectGuesses get() = _incorrectGuesses
    private val _livesLeft = MutableLiveData(8)
    val livesLeft get() = _livesLeft
    private val _gameOver = MutableLiveData(false)
    val gameOver get() = _gameOver

    init {
        _secretWordDisplay.value = deriveSecretWordDisplay()
    }

    private fun deriveSecretWordDisplay(): String {
        var display = ""
        secretWord.forEach {
            display += checkLetter(it.toString())
        }
        return display
    }
    private fun checkLetter(str: String) = when (correctGuesses.contains(str)) {
        true -> str
        false -> "_"
    }
    fun makeGuess(guess: String) {
        if (guess.length == 1) {
            if (secretWord.contains(guess)) {
                correctGuesses += guess
                _secretWordDisplay.value = deriveSecretWordDisplay()
            }
            else {
                _incorrectGuesses.value += "$guess "
                _livesLeft.value = _livesLeft.value?.minus(1)
            }
        }
        if (isWon() || isLost()) _gameOver.value = true
    }

    fun isWon() = secretWord.equals(_secretWordDisplay.value, true)

    fun isLost() = _livesLeft.value?:0 <= 0

    fun wonLostMessage(): String {
        var message = ""
        if (isWon()) message = "You won!"
        else if (isLost()) message = "You lost!"
        message += " The word was $secretWord."

        return message
    }
    fun finishGame() {
        _gameOver.value = true
    }
}
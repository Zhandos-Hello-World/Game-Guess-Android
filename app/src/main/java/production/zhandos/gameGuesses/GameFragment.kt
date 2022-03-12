package production.zhandos.gameGuesses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import production.zhandos.gameGuesses.databinding.FragmentGameBinding

class GameFragment: Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get () = _binding!!

    lateinit var viewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.gameOver.observe(viewLifecycleOwner) { newValue ->
            if (newValue) {
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(viewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        binding.guessButton.setOnClickListener {
            viewModel.makeGuess(binding.input.text.toString().uppercase())
            binding.input.text = null
        }
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
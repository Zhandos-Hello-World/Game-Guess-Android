package production.zhandos.gameGuesses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import production.zhandos.gameGuesses.databinding.FragmentResultBinding

class ResultFragment: Fragment() {
    var _binding: FragmentResultBinding? = null
    val binding get() = _binding!!

    private lateinit var viewModel: ResultViewModel
    private lateinit var viewModelFactory: ResultViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        val view = binding.root

        val result = ResultFragmentArgs.fromBundle(requireArguments()).resultMessage
        viewModelFactory = ResultViewModelFactory(result)
        viewModel = ViewModelProvider(this, viewModelFactory)[ResultViewModel::class.java]


        binding.resultViewModel = viewModel

        binding.restartButton.setOnClickListener {
            val action = ResultFragmentDirections.actionResultFragmentToGameFragment()
            view.findNavController().navigate(action)
        }
        return view
    }
}
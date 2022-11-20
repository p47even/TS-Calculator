package com.p47.tscalc

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.p47.tscalc.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

private var _binding: FragmentSecondBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
      _binding = FragmentSecondBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val link = view.findViewById(R.id.formulaLink) as TextView

        link.movementMethod = LinkMovementMethod.getInstance() //ensure link is clickable

        super.onViewCreated(view, savedInstanceState)


    }
override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPrepareOptionsMenu(menu: Menu){ //hides 3 dot menu
        val item = menu.findItem(R.id.action_settings)
        if (item != null) {
            item.setVisible(false)
        }
    }
}
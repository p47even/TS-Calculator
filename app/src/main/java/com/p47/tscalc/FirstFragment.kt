package com.p47.tscalc

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.p47.tscalc.databinding.FragmentFirstBinding
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

private var _binding: FragmentFirstBinding? = null
    private lateinit var points : EditText
    private lateinit var fga: EditText
    private lateinit var fta: EditText
    private lateinit var ts: TextView
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      _binding = FragmentFirstBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        points = view.findViewById(R.id.editP)
        fga = view.findViewById(R.id.editFGA)
        fta = view.findViewById(R.id.editFTA)
        ts = view.findViewById(R.id.textView)

        val textWatcher = object : TextWatcher { //if points, fga, or fta fields are changed, recalculate ts
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                //Toast.makeText(activity.applicationContext, "text changed", Toast.LENGTH_SHORT).show()
                calculateTS()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        }

        points.addTextChangedListener(textWatcher)
        fga.addTextChangedListener(textWatcher)
        fta.addTextChangedListener(textWatcher)

        super.onViewCreated(view, savedInstanceState)

    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calculateTS() {
    //https://www.basketball-reference.com/about/glossary.html
        var p = points.text.toString()
        var fg = fga.text.toString()
        var ft = fta.text.toString()

        val roundTo = 2

        if(p == ""){ p = "0" }
        if(fg == ""){ fg = "0" }
        if(ft == ""){ ft = "0" }

        val tsp = generateTS(p.toInt(),fg.toInt(),ft.toInt()) //calculate
        setTSColor(tsp) //change text color

        if (!tsp.isNaN() && tsp != Double.POSITIVE_INFINITY && tsp != Double.NEGATIVE_INFINITY){ //round to 2 decimal spots if possible
            val decimal = BigDecimal(tsp).setScale(roundTo, RoundingMode.HALF_EVEN)
            ts.setText( (decimal).toString()+"%" )
        }
        else {
            ts.setText(tsp.toString())
        }

    }

    private fun generateTS(points: Int, fga: Int, fta: Int) : Double{ //performs the algo
        val ftCoefficient = 0.44
        return (points / (2 * (fga + ftCoefficient * fta)))*100
    }

    private fun setTSColor(tsp: Double ){ //change text color based on efficiency
        val average = 56 //TODO: Get Actual League Average TS, move colors to colors.xml
        val slightAbove = average+2 //58
        val slightBelow = average-2 //54
        val above = slightAbove+2 //60
        val below = slightBelow-2 //52
        val red = "#C62828"
        val orange = "#D84315"
        val yellow = "#F9A825"
        val yellowGreen = "#9E9D24"
        val lightGreen = "#558B2F"
        val green = "#2E7D32"


        if(tsp == Double.POSITIVE_INFINITY || tsp >= above){ //great
            ts.setTextColor(Color.parseColor(green))
        }
        else if (tsp < above && tsp >= slightAbove){ //good
            ts.setTextColor(Color.parseColor(lightGreen))
        }
        else if  (tsp < slightAbove && tsp >= average){ //avg
            ts.setTextColor(Color.parseColor(yellowGreen))
        }
        else if (tsp < average && tsp >= slightBelow){ //below avg
            ts.setTextColor(Color.parseColor(yellow))
        }
        else if (tsp < slightBelow && tsp >= below){ //bad
            ts.setTextColor(Color.parseColor(orange))
        }
        else { //awful
            ts.setTextColor(Color.parseColor(red))
        }

    }

}
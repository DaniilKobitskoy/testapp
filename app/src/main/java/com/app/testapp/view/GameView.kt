package com.app.testapp.view




import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.testapp.R
import com.app.testapp.databinding.FragmentGameViewBinding
import com.app.testapp.model.Game

class GameView : Fragment() {
    lateinit var binding: FragmentGameViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGameView()
    }

    private fun setupGameView() {
        Elements.player1 = binding.player1label
        Elements.player2 = binding.player2label
        Elements.infoText = binding.infoText
        val resetButton = binding.resetButton
        resetButton.setOnClickListener {
            resetClick()
        }
        updateGameInfo()
    }

    private fun updateGameInfo() {
        val redColor = Color.parseColor("#FF0000")
        Elements.player1?.apply {
            text = resources.getString(R.string.player1, Elements.game.score1)
            setTextColor(redColor)
        }
        Elements.player2?.apply {
            text = resources.getString(R.string.player2, Elements.game.score2)
            setTextColor(redColor)
        }
        Elements.infoText?.apply {
            if (Elements.game.isOver) {
                text = when {
                    Elements.game.score1 > Elements.game.score2 -> resources.getString(R.string.winInfo, 1)
                    Elements.game.score2 > Elements.game.score1 -> resources.getString(R.string.winInfo, 2)
                    else -> resources.getString(R.string.drawInfo)
                }
            } else {
                text = resources.getString(
                    R.string.turnInfo,
                    if (Elements.game.turn == Game.Turn.PLAYER_ONE) 1 else 2
                )
            }
            setTextColor(redColor)
        }
    }


    private fun resetClick() {
        Elements.game = Game()
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(this@GameView.id, newInstance("", ""))
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            GameView().apply {
                arguments = Bundle().apply {

                }
            }
    }
}

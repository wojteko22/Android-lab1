package pl.edu.pwr.wojciech.okonski.lab1.lab1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.ShareActionProvider
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import pl.edu.pwr.wojciech.okonski.lab1.lab1.R.array.units
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private var bmiCounter: BmiCounter = KgMBmiCounter()
    private var shareActionProvide: ShareActionProvider by Delegates.notNull()
    private val onItemSelectedListener = object : OnItemSelectedListener {
        override fun onNothingSelected(parentView: AdapterView<*>) {}
        override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
            setBmiCounter(position)
            checkInputData()
        }
    }
    private val textWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable?) {
            checkInputData()
        }
    }

    private fun setBmiCounter(selectedItemPosition: Int) {
        bmiCounter =
                if (selectedItemPosition == 0)
                    KgMBmiCounter()
                else
                    LbInBmiCounter()
    }

    private fun checkInputData() {
        val isMassValid = checkMassInput()
        val isHeightValid = checkHeightInput()
        val isInputValid = isMassValid && isHeightValid
        btnSave.isEnabled = isInputValid
        btnCount.isEnabled = isInputValid
    }

    private fun checkMassInput(): Boolean {
        return try {
            checkMass()
        } catch (e: NumberFormatException) {
            etMass.error = getString(R.string.NaN)
            false
        }
    }

    private fun checkMass(): Boolean {
        val isValid = bmiCounter.isMassValid(getMass())
        if (!isValid) {
            val template = getString(R.string.inputRange)
            etMass.error = String.format(template, bmiCounter.minimalMass, bmiCounter.maximalMass)
        }
        return isValid
    }

    private fun checkHeightInput(): Boolean {
        return try {
            checkHeight()
        } catch (e: NumberFormatException) {
            etHeight.error = getString(R.string.NaN)
            false
        }
    }

    private fun checkHeight(): Boolean {
        val isValid = bmiCounter.isHeightValid(getHeight())
        if (!isValid) {
            val template = getString(R.string.inputRange)
            etHeight.error = String.format(template, bmiCounter.minimalHeight, bmiCounter.maximalHeight)
        }
        return isValid
    }

    private fun getMass() = getMassString().toFloat()
    private fun getMassString() = etMass.text.toString()
    private fun getHeight() = getHeightString().toFloat()
    private fun getHeightString() = etHeight.text.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setSpinner()
        readSavedData()
        setListeners()
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(this, units, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = onItemSelectedListener
    }

    private fun readSavedData() {
        val inputData = getPreferences(Context.MODE_PRIVATE)
        etMass.setText(inputData.getString(MASS, ""))
        etHeight.setText(inputData.getString(HEIGHT, ""))
        spinner.setSelection(inputData.getInt(SPINNER_POSITION, 0))
    }

    private fun setListeners() {
        btnCount.setOnClickListener { displayBmiStuff() }
        etMass.addTextChangedListener(textWatcher)
        etHeight.addTextChangedListener(textWatcher)
        btnSave.setOnClickListener { handleSavingInputData() }
    }

    private fun displayBmiStuff() {
        hideSoftKeyboard(this)
        calculateBmi()
        invalidateOptionsMenu()
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }

    private fun calculateBmi() {
        val bmi = bmiCounter.calculateBMI(getMass(), getHeight()).toString()
        tvBmiResult.text = bmi
        setFireWorks(bmi.toFloat())
    }

    private fun setFireWorks(bmi: Float) {
        when (bmi) {
            in 30f..Float.POSITIVE_INFINITY -> {
                setDescription(R.string.obese)
                setColor(Color.RED)
            }
            in 25f..30f -> {
                setDescription(R.string.overweight)
                setColor(Color.MAGENTA)
            }
            in 18.5f..25f -> {
                setDescription(R.string.normalBmi)
                setColor(Color.GREEN)
            }
            in 16.5f..18.5f -> {
                setDescription(R.string.underweight)
                setColor(Color.MAGENTA)
            }
            else -> {
                setDescription(R.string.seriouslyUnderweight)
                setColor(Color.RED)
            }
        }
    }

    private fun setDescription(stringId: Int) {
        tvDescription.text = getString(stringId)
    }

    private fun setColor(color: Int) {
        tvBmiResult.setTextColor(color)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val textColor = savedInstanceState.getInt(COLOR)
        tvBmiResult.setTextColor(textColor)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val textColor = tvBmiResult.textColors.defaultColor
        outState.putInt(COLOR, textColor)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        findShareActionProvider(menu)
        return true
    }

    private fun findShareActionProvider(menu: Menu) {
        val shareItem = menu.findItem(R.id.share)
        shareActionProvide = MenuItemCompat.getActionProvider(shareItem) as ShareActionProvider
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        showShareItemIfBmiIsCounted(menu)
        prepareSharingIntent()
        return true
    }

    private fun showShareItemIfBmiIsCounted(menu: Menu) {
        menu.findItem(R.id.share).isVisible = tvBmiResult.text.isNotEmpty()
    }

    private fun prepareSharingIntent() {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT, getSharingMessage())
        sendIntent.type = "text/plain"
        shareActionProvide.setShareIntent(sendIntent)
    }

    private fun getSharingMessage(): String {
        val sharingMessageTemplate = getString(R.string.sharingMessage)
        return String.format(sharingMessageTemplate, tvBmiResult.text)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.author -> {
                startActivity<AuthorActivity>()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleSavingInputData() {
        saveInputData()
        btnSave.isEnabled = false
        toast(getString(R.string.saved))
    }

    private fun saveInputData() {
        val inputData = getPreferences(Context.MODE_PRIVATE)
        val editor = inputData.edit()
        putData(editor)
        editor.apply()
    }

    private fun putData(editor: SharedPreferences.Editor) {
        editor.putString(MASS, getMassString())
        editor.putString(HEIGHT, getHeightString())
        editor.putInt(SPINNER_POSITION, spinner.selectedItemPosition)
    }

    companion object {
        private const val COLOR = "COLOR"
        private const val MASS = "MASS"
        private const val HEIGHT = "HEIGHT"
        private const val SPINNER_POSITION = "SPINNER_POSITION"
    }
}
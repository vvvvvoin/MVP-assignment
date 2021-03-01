package com.example.myfriend.view.home.addEdit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myfriend.R
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentAddEditBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.ext.android.inject
import java.util.*


class AddEditFragment : Fragment(), AddEditContract.View {
    private val TAG = "AddEditFragment"

    companion object {
        const val ADD_OR_EDIT_REQUEST_KEY = "addOrEditRequestCode"
        const val ADD_OR_EDIT_BUNDLE_KEY = "addOrEditBundleKey"
        const val TASK_FLAG =  Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
    }

    private lateinit var mPresenter: AddEditContract.Presenter

    private lateinit var binding: FragmentAddEditBinding
    private var bottomNavigationView : BottomNavigationView? = null

    //이 view 를 불러오는 view 로부터 id 값이 존재하면 기존에 있는 것을 수정하는 것이고
    //id 값이 없을 경우 새롭게 추가하는 항목이다
    private val args: AddEditFragmentArgs by navArgs()
    private val myRepository: MyRepository by inject()

    val requestActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        // action to do something
        if(activityResult.resultCode == Activity.RESULT_OK){
            receivedProfileUri = activityResult.data?.data
            receivedProfileUri?.let {
                activity?.contentResolver?.takePersistableUriPermission(it, TASK_FLAG)
                setProfileImage(receivedProfileUri, binding.profileImageView)
            }
        }
    }

    private var receivedNationData : com.example.myfriend.model.vo.Nation? = null
    private var receivedProfileUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(ADD_OR_EDIT_REQUEST_KEY) { key, bundle ->
            receivedNationData = bundle.getParcelable(ADD_OR_EDIT_BUNDLE_KEY)
            receivedNationData?.let {
                val uri = ("https://flagcdn.com/h80/" + it.alpha2Code.toLowerCase(Locale.ROOT) +".png").toUri()
                Glide.with(this).load(uri).override(Target.SIZE_ORIGINAL).into(binding.flagImageView)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bottomNavigationView= activity?.findViewById(R.id.bottom_nav)
        bottomNavigationView?.let {
            it.visibility = View.GONE
        }
        Log.d(TAG, "AddEditFragment이 새롭게 생성되었어요")
        Log.d(TAG, "${myRepository.toString()}")
        mPresenter = AddEditPresenter(myRepository, args.friendId)
        mPresenter.setView(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit, container, false)
        val view = binding.root

        binding.flagImageView.setOnClickListener {
            AddEditFragmentDirections.actionAddEditFragmentToNationFragment3(true).also {
                findNavController().navigate(it)
            }
        }

        binding.profileImageView.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_OPEN_DOCUMENT
            requestActivity.launch(intent)
        }

        setProfileImage(receivedProfileUri, binding.profileImageView)
        setHasOptionsMenu(true)
        return view
    }

    private fun setProfileImage(uri: Uri?, view : ImageView){
        Glide.with(this).load(uri).into(view)
    }

    private fun showMessage(str : String){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.add_edit_btn -> {
                if(receivedNationData != null){
                    val profileUri = receivedProfileUri
                    val name = binding.nameEdit.text.toString()
                    val number = binding.numberEdit.text.toString()
                    val email = binding.emailEdit.text.toString()
                    val flagUri = receivedNationData!!.alpha2Code
                    val nation = receivedNationData!!.name
                    if (name.isEmpty()) {
                        showMessage("이름입력은 필수 입니다.")
                        return true
                    }
                    if (number.isEmpty() && email.isEmpty()) {
                        showMessage("전화번호와 이메일 중 하나는 반드시 입력해야합니다.")
                        return true
                    }
                    //권한부여
                    profileUri?.let {
                        activity?.contentResolver?.takePersistableUriPermission(profileUri, TASK_FLAG)
                    }
                    mPresenter.addEdit(name, number,email, flagUri, nation, profileUri.toString())
                }else{
                    showMessage("국가선택은 필수 입니다.")
                    return true
                }
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.addedit_menu, menu)
    }

    override fun setPresenter(presenter: AddEditContract.Presenter) {
        mPresenter = presenter
    }

    override fun errorMessage(error: String) {

    }

    override fun completeAddEdit() {
        bottomNavigationView?.let {
            it.visibility = View.VISIBLE
        }
        activity?.onBackPressed()
    }
}
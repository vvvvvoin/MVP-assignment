package com.example.myfriend.view.home.addEdit

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.setFragmentResultListener
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myfriend.R
import com.example.myfriend.data.dataSource.remoteData.NationW
import com.example.myfriend.data.db.entity.Friend
import com.example.myfriend.data.db.entity.Tag
import com.example.myfriend.data.repository.MyRepository
import com.example.myfriend.databinding.FragmentAddEditBinding
import com.example.myfriend.view.nation.NationFragment
import org.koin.android.ext.android.inject
import java.util.*
import java.util.regex.Pattern


class AddEditFragment : Fragment(), AddEditContract.View {
    private val TAG = "AddEditFragment"

    companion object {
        const val ADD_OR_EDIT_REQUEST_KEY = "addOrEditRequestCode"
        const val ADD_OR_EDIT_BUNDLE_KEY = "addOrEditBundleKey"
        const val TASK_FLAG =  Intent.FLAG_GRANT_READ_URI_PERMISSION or
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        const val NUMBER_REGEX = "^\\d{3}-\\d{3,4}-\\d{4}$"
        const val EMAIL_REGEX = "^[_a-zA-Z0-9-.]+@[.a-zA-Z0-9-]+\\.[a-zA-Z]+$"
    }

    private lateinit var mPresenter: AddEditContract.Presenter

    private lateinit var binding: FragmentAddEditBinding

    private val myRepository: MyRepository by inject()
    private val addEditTagAdapter: AddEditTagAdapter by lazy {
        AddEditTagAdapter(true)
    }

    private var receivedNationWData : NationW? = null
    private var receivedProfileUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(ADD_OR_EDIT_REQUEST_KEY) { key, bundle ->
            receivedNationWData = bundle.getParcelable(ADD_OR_EDIT_BUNDLE_KEY)
            receivedNationWData?.let {
                val uri = ("https://flagcdn.com/h80/" + it.alpha2Code.toLowerCase(Locale.ROOT) +".png").toUri()
                Glide.with(this).load(uri).override(Target.SIZE_ORIGINAL).into(binding.flagImageView)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val friendData = arguments?.getParcelable<Friend>("FRIEND_DATA")
        val tagList = arguments?.getParcelableArrayList<Tag>("TAG_DATA")
        if (tagList != null) {
            addEditTagAdapter.tagList = tagList
        }
        if(friendData != null){
            receivedNationWData = NationW(name = friendData.name, alpha2Code = friendData.flagUri)
            receivedProfileUri = friendData.profile?.toUri()
        }

        mPresenter = AddEditPresenter(myRepository, friendData?.id)
        mPresenter.setView(this)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit, container, false)
        binding.data = friendData
        binding.tagItemRecycler.apply {
            adapter = addEditTagAdapter
            setHasFixedSize(true)
        }

        binding.addTagBtn.setOnClickListener {
            showDialog()
        }

        val view = binding.root

        binding.flagImageView.setOnClickListener {
            val transaction: FragmentTransaction = parentFragmentManager.beginTransaction()
            val a = NationFragment()
            val bundle = Bundle()
            bundle.putBoolean(NationFragment.IS_ADD_OR_EDIT, true)
            a.arguments = bundle
            transaction.add(R.id.contentFrame, a)
            transaction.addToBackStack(null)
            transaction.commit()
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

    private val requestActivity = registerForActivityResult(
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
    private fun showDialog(){
        val dialogView = layoutInflater.inflate(R.layout.custom_tag_dialog, null)
        val editText = dialogView.findViewById<EditText>(R.id.custom_tag_dialog_edit)
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
            .setTitle("태그")
            .setMessage("태그를 입력하세요")
            .setPositiveButton("확인") { dialogInterface, i ->
                addEditTagAdapter.tagList.add(Tag(editText.text.toString()))
                addEditTagAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("취소") { dialogInterface, i ->
            }
            .show()
    }
    private fun setProfileImage(uri: Uri?, view: ImageView){
        Glide.with(this).load(uri).into(view)
    }

    private fun showMessage(str: String){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                activity?.onBackPressed()
            }
            R.id.add_edit_btn -> {
                if (receivedNationWData != null) {
                    val profileUri = receivedProfileUri
                    val name = binding.nameEdit.text.toString()
                    val number = binding.numberEdit.text.toString()
                    val email = binding.emailEdit.text.toString()
                    val flagUri = receivedNationWData!!.alpha2Code
                    val nation = receivedNationWData!!.name
                    if (name.isEmpty()) {
                        showMessage("이름입력은 필수 입니다.")
                        return true
                    } else if (number.isNotEmpty() && !Pattern.matches(NUMBER_REGEX, number)) {
                        showMessage("올바른 번호 형식으로 입력해주세요.")
                        return true
                    } else if (number.isEmpty() && email.isEmpty()) {
                        showMessage("전화번호와 이메일 중 하나는 반드시 입력해야합니다.")
                        return true
                    } else if (email.isNotEmpty() && !Pattern.matches(EMAIL_REGEX, email)) {
                        showMessage("올바른 이메일 형식으로 입력해주세요.")
                        return true
                    }
                    //권한부여
                    if(profileUri != null && profileUri.toString() != "null"){
                        activity?.contentResolver?.takePersistableUriPermission(
                            profileUri,
                            TASK_FLAG
                        )
                    }
                    mPresenter.addEdit(name, number, email, flagUri, nation, profileUri.toString(), addEditTagAdapter.tagList)
                } else {
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
        activity?.setResult(AddEditActivity.EDIT_RESULT_OK)
        activity?.finish()
    }
}
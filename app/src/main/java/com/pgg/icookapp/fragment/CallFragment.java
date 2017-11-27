package com.pgg.icookapp.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseContactList;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.UIUtils;

import com.pgg.icookapp.activity.ChatActivity;
import com.pgg.icookapp.view.LoadingPage;

import java.util.ArrayList;


/**
 * Created by PDD on 2017/7/23.
 */
public class CallFragment extends BaseFragment {

    private ArrayList<EaseUser> list;



    @Override
    public View onCreateSuccessPage() {
        View view=UIUtils.getView(R.layout.fragment_call);
        EaseContactList contact_list=(EaseContactList)view.findViewById(R.id.contact_list);
        //初始化时需要传入联系人list
        contact_list.init(list);
        //刷新列表
        contact_list.refresh();

        contact_list.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(UIUtils.getContext(),"当前触摸的是："+list.get(position).getUsername(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(UIUtils.getContext(), ChatActivity.class);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,list.get(position).getUsername());
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        list=new ArrayList<>();
        String[] name=new String[]{"18829224913","童话","花斑鹿","美杜莎","听雨书","胖哥哥飘过","chimchim","春风一里","冰冰","春风十里","背对背拥抱","18829591066"};
        for (int i=0;i<name.length;i++){
            EaseUser easeUser=new EaseUser(name[i]);
            list.add(easeUser);
        }
        return checkData(list);
    }
}

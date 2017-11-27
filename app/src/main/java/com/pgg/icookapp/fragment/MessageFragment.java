package com.pgg.icookapp.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseConversationList;
import com.pgg.icookapp.R;
import com.pgg.icookapp.Utils.UIUtils;
import com.pgg.icookapp.activity.ChatActivity;
import com.pgg.icookapp.view.LoadingPage;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by PDD on 2017/7/23.
 */
public class MessageFragment extends BaseFragment {
    private Map<String, EMConversation> listmap;
    private ArrayList<EMConversation> list;

    @Override
    public View onCreateSuccessPage() {
        View view=UIUtils.getView(R.layout.fragment_message);
        EaseConversationList list_conversation=(EaseConversationList)view.findViewById(R.id.list);
        //初始化，参数为会话列表集合
        list_conversation.init(list);
        //刷新列表
        list_conversation.refresh();
        list_conversation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(UIUtils.getContext(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, list.get(position).conversationId()));
            }
        });
        return view;
    }

    @Override
    public LoadingPage.ResultState onLoad() {
        listmap = EMClient.getInstance().chatManager().getAllConversations();
        list = new ArrayList<>();
        for (String s:listmap.keySet()){
             EMConversation emConversation=listmap.get(s);
             list.add(emConversation);
        }
        return checkData(list);
    }
}

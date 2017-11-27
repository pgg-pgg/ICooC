package com.pgg.icookapp.fragment;

import java.util.HashMap;



/**
 * Created by PDD on 2017/7/23.
 */
public class FactoryFragment {
    private static HashMap<Integer,BaseFragment> hashMapfragment=new HashMap<Integer,BaseFragment>();
    public static BaseFragment createFragment(int pos){
        BaseFragment baseFragment=hashMapfragment.get(pos);
        if (baseFragment==null){
            switch (pos){
                case 0:
                    baseFragment=new HomeFragment();
                    break;
                case 1:
                    baseFragment=new CallFragment();
                    break;
                case 2:
                    baseFragment=new MessageFragment();
                    break;
                case 3:
                    baseFragment=new MeFragment();
                    break;
                default:
                    break;
            }
        }
        hashMapfragment.put(pos,baseFragment);
        return baseFragment;
    }
}

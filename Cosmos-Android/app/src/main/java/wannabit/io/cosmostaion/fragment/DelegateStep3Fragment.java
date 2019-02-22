package wannabit.io.cosmostaion.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;

import wannabit.io.cosmostaion.R;
import wannabit.io.cosmostaion.activities.DelegateActivity;
import wannabit.io.cosmostaion.base.BaseConstant;
import wannabit.io.cosmostaion.base.BaseFragment;
import wannabit.io.cosmostaion.utils.WDp;
import wannabit.io.cosmostaion.utils.WLog;

public class DelegateStep3Fragment extends BaseFragment implements View.OnClickListener {

    private TextView        mDelegateAtomAmount;
    private TextView        mFeeAmount, mFeeType;
    private TextView        mValidatorName, mMemo;
    private TextView        mRemindAtom, mRemindPhoton;
    private Button          mBeforeBtn, mConfirmBtn;


    public static DelegateStep3Fragment newInstance(Bundle bundle) {
        DelegateStep3Fragment fragment = new DelegateStep3Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_delegate_step3, container, false);
        mDelegateAtomAmount     = rootView.findViewById(R.id.delegate_atom);
        mFeeAmount              = rootView.findViewById(R.id.delegate_fees);
        mFeeType                = rootView.findViewById(R.id.delegate_fees_type);
        mValidatorName          = rootView.findViewById(R.id.to_delegate_moniker);
        mMemo                   = rootView.findViewById(R.id.memo);
        mRemindAtom             = rootView.findViewById(R.id.remind_atom);
        mRemindPhoton           = rootView.findViewById(R.id.remind_photon);
        mBeforeBtn              = rootView.findViewById(R.id.btn_before);
        mConfirmBtn             = rootView.findViewById(R.id.btn_confirm);
        mBeforeBtn.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onRefreshTab() {
        BigDecimal toSendAtom = new BigDecimal(getSActivity().mToDelegateAmount);
        BigDecimal remindAtom = getSActivity().mAccount.getAtomBalance().subtract(toSendAtom);
        BigDecimal remindPhoton = BigDecimal.ZERO;

        if(getSActivity().mToDelegateFee.denom.equals(BaseConstant.COSMOS_ATOM)) {
            mFeeType.setText(BaseConstant.COSMOS_ATOM);
            mFeeType.setTextColor(getResources().getColor(R.color.colorAtom));
            remindAtom.subtract(new BigDecimal(getSActivity().mToDelegateFee.amount));
        } else {
            mFeeType.setText(BaseConstant.COSMOS_PHOTON);
            mFeeType.setTextColor(getResources().getColor(R.color.colorPhoton));
            remindPhoton.subtract(new BigDecimal(getSActivity().mToDelegateFee.amount));
        }

        mFeeAmount.setText(WDp.getDpAmount(getContext(), new BigDecimal(getSActivity().mToDelegateFee.amount), 6));
        mValidatorName.setText(getSActivity().mValidator.description.moniker);
        mMemo.setText(getSActivity().mToDelegateMemo);

        mRemindAtom.setText(WDp.getDpAmount(getContext(), remindAtom, 6));
        mRemindPhoton.setText(WDp.getDpAmount(getContext(), remindPhoton, 6));
    }

    @Override
    public void onClick(View v) {
        if(v.equals(mBeforeBtn)) {
            getSActivity().onBeforeStep();

        } else if (v.equals(mConfirmBtn)) {
            WLog.w("mConfirmBtn");

        }

    }

    private DelegateActivity getSActivity() {
        return (DelegateActivity)getBaseActivity();
    }


}
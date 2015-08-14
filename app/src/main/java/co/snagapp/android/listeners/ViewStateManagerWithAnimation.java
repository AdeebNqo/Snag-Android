package co.snagapp.android.listeners;

import android.view.View;
import com.google.inject.Singleton;
import java.util.List;
import co.snagapp.android.worker.ViewStateManager;

/**
 * Created on 2015/08/13.
 */
@Singleton
public class ViewStateManagerWithAnimation implements ViewStateManager {

    private List<? extends Object> numbers;
    private View emptyStateView;
    private View nonEmptyStateView;

    public void setItems(List<? extends Object> items){
        numbers = items;
    }

    public void setNonEmptyStateView(View nonEmptyStateView) {
        this.nonEmptyStateView = nonEmptyStateView;
    }

    public void setEmptyStateView(View emptyStateView) {
        this.emptyStateView = emptyStateView;
    }

    public void setStates(View emptyStateView, View nonEmptyStateView) {
        this.emptyStateView = emptyStateView;
        this.nonEmptyStateView = nonEmptyStateView;
    }


    public void renderCurrentViewState(){
        if (numbers!=null && emptyStateView != null && nonEmptyStateView != null ){
            if (!numbers.isEmpty()){
                emptyStateView.setVisibility(View.INVISIBLE);
                nonEmptyStateView.setVisibility(View.VISIBLE);
            }else{
                emptyStateView.setVisibility(View.VISIBLE);
                nonEmptyStateView.setVisibility(View.INVISIBLE);
            }
        }
    }
}

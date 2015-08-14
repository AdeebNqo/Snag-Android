package co.snagapp.android.worker;

import android.view.View;

import java.util.List;

/**
 * Created on 2015/08/13.
 */
public interface ViewStateManager {
    void setItems(List<? extends Object> items);
    void setNonEmptyStateView(View nonEmptyStateView);
    void setEmptyStateView(View emptyStateView);
    void setStates(View emptyStateView, View nonEmptyStateView);
    void renderCurrentViewState();
}

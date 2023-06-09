// Generated by view binder compiler. Do not edit!
package com.myscan.goscan.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.myscan.goscan.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class GroceriesHistoryItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView tvProductAmount;

  @NonNull
  public final TextView tvProductDate;

  @NonNull
  public final TextView tvProductName;

  @NonNull
  public final TextView tvProductPrice;

  private GroceriesHistoryItemBinding(@NonNull LinearLayout rootView,
      @NonNull TextView tvProductAmount, @NonNull TextView tvProductDate,
      @NonNull TextView tvProductName, @NonNull TextView tvProductPrice) {
    this.rootView = rootView;
    this.tvProductAmount = tvProductAmount;
    this.tvProductDate = tvProductDate;
    this.tvProductName = tvProductName;
    this.tvProductPrice = tvProductPrice;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static GroceriesHistoryItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static GroceriesHistoryItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.groceries_history_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static GroceriesHistoryItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.tvProductAmount;
      TextView tvProductAmount = ViewBindings.findChildViewById(rootView, id);
      if (tvProductAmount == null) {
        break missingId;
      }

      id = R.id.tvProductDate;
      TextView tvProductDate = ViewBindings.findChildViewById(rootView, id);
      if (tvProductDate == null) {
        break missingId;
      }

      id = R.id.tvProductName;
      TextView tvProductName = ViewBindings.findChildViewById(rootView, id);
      if (tvProductName == null) {
        break missingId;
      }

      id = R.id.tvProductPrice;
      TextView tvProductPrice = ViewBindings.findChildViewById(rootView, id);
      if (tvProductPrice == null) {
        break missingId;
      }

      return new GroceriesHistoryItemBinding((LinearLayout) rootView, tvProductAmount,
          tvProductDate, tvProductName, tvProductPrice);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}

// Generated by view binder compiler. Do not edit!
package com.myscan.goscan.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Guideline;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.imageview.ShapeableImageView;
import com.myscan.goscan.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class GroceriesListBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final Guideline guideline2;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final ImageView imageView3;

  @NonNull
  public final ShapeableImageView shapeableImageView;

  @NonNull
  public final TextView tvDate;

  @NonNull
  public final TextView tvGroceriesBrand;

  @NonNull
  public final TextView tvGroceriesPrice;

  @NonNull
  public final TextView tvGroceriesTotal;

  private GroceriesListBinding(@NonNull CardView rootView, @NonNull Guideline guideline2,
      @NonNull ImageView imageView, @NonNull ImageView imageView2, @NonNull ImageView imageView3,
      @NonNull ShapeableImageView shapeableImageView, @NonNull TextView tvDate,
      @NonNull TextView tvGroceriesBrand, @NonNull TextView tvGroceriesPrice,
      @NonNull TextView tvGroceriesTotal) {
    this.rootView = rootView;
    this.guideline2 = guideline2;
    this.imageView = imageView;
    this.imageView2 = imageView2;
    this.imageView3 = imageView3;
    this.shapeableImageView = shapeableImageView;
    this.tvDate = tvDate;
    this.tvGroceriesBrand = tvGroceriesBrand;
    this.tvGroceriesPrice = tvGroceriesPrice;
    this.tvGroceriesTotal = tvGroceriesTotal;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static GroceriesListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static GroceriesListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.groceries_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static GroceriesListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.guideline2;
      Guideline guideline2 = ViewBindings.findChildViewById(rootView, id);
      if (guideline2 == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.imageView2;
      ImageView imageView2 = ViewBindings.findChildViewById(rootView, id);
      if (imageView2 == null) {
        break missingId;
      }

      id = R.id.imageView3;
      ImageView imageView3 = ViewBindings.findChildViewById(rootView, id);
      if (imageView3 == null) {
        break missingId;
      }

      id = R.id.shapeableImageView;
      ShapeableImageView shapeableImageView = ViewBindings.findChildViewById(rootView, id);
      if (shapeableImageView == null) {
        break missingId;
      }

      id = R.id.tvDate;
      TextView tvDate = ViewBindings.findChildViewById(rootView, id);
      if (tvDate == null) {
        break missingId;
      }

      id = R.id.tvGroceriesBrand;
      TextView tvGroceriesBrand = ViewBindings.findChildViewById(rootView, id);
      if (tvGroceriesBrand == null) {
        break missingId;
      }

      id = R.id.tvGroceriesPrice;
      TextView tvGroceriesPrice = ViewBindings.findChildViewById(rootView, id);
      if (tvGroceriesPrice == null) {
        break missingId;
      }

      id = R.id.tvGroceriesTotal;
      TextView tvGroceriesTotal = ViewBindings.findChildViewById(rootView, id);
      if (tvGroceriesTotal == null) {
        break missingId;
      }

      return new GroceriesListBinding((CardView) rootView, guideline2, imageView, imageView2,
          imageView3, shapeableImageView, tvDate, tvGroceriesBrand, tvGroceriesPrice,
          tvGroceriesTotal);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
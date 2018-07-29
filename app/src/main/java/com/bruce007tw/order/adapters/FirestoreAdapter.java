package com.bruce007tw.order.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public abstract class FirestoreAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> implements EventListener<QuerySnapshot>{

    private static final String TAG = "FirestoreAdapter";

    private Query mQuery;
    private ListenerRegistration mListenerRegistration;
    protected ArrayList<DocumentSnapshot> mDocumentSnapshot = new ArrayList<>();

    public FirestoreAdapter(Query mQuery) {
        this.mQuery = mQuery;
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot mDocumentSnapshot, @Nullable FirebaseFirestoreException e) {
        if (e != null) {
            Log.d(TAG, "onEvent 錯誤：", e);
            onError(e);
            return;
        }
        for (DocumentChange change : mDocumentSnapshot.getDocumentChanges()) {
            switch (change.getType()) {
                case ADDED:
                    onDocumentAdded(change);
                    break;
                case MODIFIED:
                    onDocumentModified(change);
                    break;
                case REMOVED:
                    onDocumentRemoved(change);
                    break;
            }
        }
        onEventTriggered();
    }

    public void onEventTriggered() {
    }

    public void startListening() {
        if (mQuery != null && mListenerRegistration == null) {
            mListenerRegistration = mQuery.addSnapshotListener(this);
        }
    }

    public void stopListening() {
        if (mListenerRegistration != null) {
            mListenerRegistration.remove();
            mListenerRegistration = null;
        }

        mDocumentSnapshot.clear();
        notifyDataSetChanged();
    }

    protected DocumentSnapshot getSnapshot(int index) {
        return mDocumentSnapshot.get(index);
    }

    private void onDocumentAdded(DocumentChange change) {
        mDocumentSnapshot.add(change.getNewIndex(), change.getDocument());
        notifyItemInserted(change.getNewIndex());
    }

    private void onDocumentModified(DocumentChange change) {
        if (change.getOldIndex() == change.getNewIndex()) {
            mDocumentSnapshot.set(change.getOldIndex(), change.getDocument());
            notifyItemChanged(change.getOldIndex());
        } else {
            mDocumentSnapshot.remove(change.getOldIndex());
            mDocumentSnapshot.add(change.getNewIndex(), change.getDocument());
            notifyItemMoved(change.getOldIndex(), change.getNewIndex());
        }
    }

    private void onDocumentRemoved(DocumentChange change) {
        mDocumentSnapshot.remove(change.getOldIndex());
        notifyItemRemoved(change.getOldIndex());
    }

    protected void onError(FirebaseFirestoreException e) {
        Log.d(TAG, "錯誤：", e);
    }
}

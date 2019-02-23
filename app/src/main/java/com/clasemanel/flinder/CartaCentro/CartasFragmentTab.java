package com.clasemanel.flinder.CartaCentro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.util.DiffUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.clasemanel.flinder.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.RewindAnimationSetting;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import java.util.ArrayList;
import java.util.List;

public class CartasFragmentTab extends Fragment implements CardStackListener {
    private DrawerLayout drawerLayout;

    private CardStackLayoutManager manager;
    private CardStackAdapter adapter;
    private CardStackView cardStackView;

    private DatabaseReference bbdd;
    private DatabaseReference bbddUser;
    private FirebaseAuth mAuth;
    private FirebaseUser usuario;

    private List<Spot> listaUsers;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        // setupNavigation();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_carta_tab, container, false);

        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();
        bbdd = FirebaseDatabase.getInstance().getReference("Usuarios");
        listaUsers = new ArrayList<Spot>();

        setupCardStackView(v);
        setupButton(v);
        rellenarUsers();

        return  v;

    }
   /* @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public void onCardDragging(Direction direction, float ratio) {
        Log.d("CardStackView", "onCardDragging: d = " + direction.name() + ", r = " + ratio);
    }

    @Override
    public void onCardSwiped(Direction direction) {
        Log.d("CardStackView", "onCardSwiped: p = " + manager.getTopPosition() + ", d = " + direction);
        if (manager.getTopPosition() == adapter.getItemCount() - 5) {
            paginate();
        }
    }

    @Override
    public void onCardRewound() {
        Log.d("CardStackView", "onCardRewound: " + manager.getTopPosition());
    }

    @Override
    public void onCardCanceled() {
        Log.d("CardStackView", "onCardCanceled:" + manager.getTopPosition());
    }

    @Override
    public void onCardAppeared(View view, int position) {
        TextView textView = view.findViewById(R.id.item_name);
        Log.d("CardStackView", "onCardAppeared: (" + position + ") " + textView.getText());
    }

    @Override
    public void onCardDisappeared(View view, int position) {
        TextView textView = view.findViewById(R.id.item_name);
        Log.d("CardStackView", "onCardDisappeared: (" + position + ") " + textView.getText());
    }

  /*  private void setupNavigation() {
        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // NavigationView
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.reload:
                        reload();
                        break;
                    case R.id.add_one_spot_at_first:
                        addFirst(1);
                        break;
                    case R.id.add_two_spots_at_first:
                        addFirst(2);
                        break;
                    case R.id.add_one_spot_at_last:
                        addLast(1);
                        break;
                    case R.id.add_two_spots_at_last:
                        addLast(2);
                        break;
                    case R.id.remove_one_spot_at_first:
                        removeFirst(1);
                        break;
                    case R.id.remove_two_spots_at_first:
                        removeFirst(2);
                        break;
                    case R.id.remove_one_spot_at_last:
                        removeLast(1);
                        break;
                    case R.id.remove_two_spots_at_last:
                        removeLast(2);
                        break;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }*/

    private void setupCardStackView(View v) {
        initialize(v);
    }

    private void setupButton(View v) {
        View skip = v.findViewById(R.id.skip_button);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Left)
                        .setDuration(200)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                manager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
            }
        });

        View rewind = v.findViewById(R.id.rewind_button);
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RewindAnimationSetting setting = new RewindAnimationSetting.Builder()
                        .setDirection(Direction.Bottom)
                        .setDuration(200)
                        .setInterpolator(new DecelerateInterpolator())
                        .build();
                manager.setRewindAnimationSetting(setting);
                cardStackView.rewind();
            }
        });

        View like = v.findViewById(R.id.like_button);
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder()
                        .setDirection(Direction.Right)
                        .setDuration(200)
                        .setInterpolator(new AccelerateInterpolator())
                        .build();
                manager.setSwipeAnimationSetting(setting);
                cardStackView.swipe();
            }
        });
    }

    private void initialize(View v) {
        manager = new CardStackLayoutManager(getContext(), this);
        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.HORIZONTAL);
        manager.setCanScrollHorizontal(true);
        manager.setCanScrollVertical(true);
        cardStackView = v.findViewById(R.id.card_stack_view);
    }

    private void paginate() {
        List<Spot> oldList = adapter.getSpots();
        List<Spot> newList = new ArrayList<Spot>() {{
            addAll(adapter.getSpots());
            addAll(listaUsers);
        }};
        SpotDiffCallBack callback = new SpotDiffCallBack(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setSpots(newList);
        result.dispatchUpdatesTo(adapter);
    }

   /* private void reload() {
        List<Spot> oldList = adapter.getSpots();
        List<Spot> newList = createSpots();
        SpotDiffCallback callback = new SpotDiffCallback(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setSpots(newList);
        result.dispatchUpdatesTo(adapter);
    }

    private void addFirst(final int size) {
        List<Spot> oldList = adapter.getSpots();
        List<Spot> newList = new ArrayList<Spot>() {{
            addAll(adapter.getSpots());
            for (int i = 0; i < size; i++) {
                add(manager.getTopPosition(), createSpot());
            }
        }};
        SpotDiffCallback callback = new SpotDiffCallback(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setSpots(newList);
        result.dispatchUpdatesTo(adapter);
    }

    private void addLast(final int size) {
        List<Spot> oldList = adapter.getSpots();
        List<Spot> newList = new ArrayList<Spot>() {{
            addAll(adapter.getSpots());
            for (int i = 0; i < size; i++) {
                add(createSpot());
            }
        }};
        SpotDiffCallback callback = new SpotDiffCallback(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setSpots(newList);
        result.dispatchUpdatesTo(adapter);
    }*/

    private void removeFirst(final int size) {
        if (adapter.getSpots().isEmpty()) {
            return;
        }

        List<Spot> oldList = adapter.getSpots();
        List<Spot> newList = new ArrayList<Spot>() {{
            addAll(adapter.getSpots());
            for (int i = 0; i < size; i++) {
                remove(manager.getTopPosition());
            }
        }};
        SpotDiffCallBack callback = new SpotDiffCallBack(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setSpots(newList);
        result.dispatchUpdatesTo(adapter);
    }

    private void removeLast(final int size) {
        if (adapter.getSpots().isEmpty()) {
            return;
        }

        List<Spot> oldList = adapter.getSpots();
        List<Spot> newList = new ArrayList<Spot>() {{
            addAll(adapter.getSpots());
            for (int i = 0; i < size; i++) {
                remove(size() - 1);
            }
        }};
        SpotDiffCallBack callback = new SpotDiffCallBack(oldList, newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        adapter.setSpots(newList);
        result.dispatchUpdatesTo(adapter);
    }


    private void rellenarUsers()
    {
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot user: dataSnapshot.getChildren())
                {
                    Spot aux = new Spot();

                    aux.setId(user.getKey());
                    aux.setName(user.child("nombre").getValue().toString());
                    aux.setCity(user.child("localidad").getValue().toString());
                    aux.setUrl(user.child("imagenes").child("img1").child("nombre").getValue().toString());

                    listaUsers.add(aux);
                }

                iniciarRecycler();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void iniciarRecycler()
    {
        adapter = new CardStackAdapter(getContext(),listaUsers);
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
    }
}

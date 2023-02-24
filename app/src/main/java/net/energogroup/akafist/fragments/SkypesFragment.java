package net.energogroup.akafist.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.energogroup.akafist.MainActivity;
import net.energogroup.akafist.databinding.FragmentSkypesBinding;
import net.energogroup.akafist.recyclers.SkypesRecyclerAdapter;
import net.energogroup.akafist.viewmodel.SkypeViewModel;

/**
 * Класс фрагмента со списком онлайн-конфереций
 * @author Nastya Izotina
 * @version 1.0.0
 */
public class SkypesFragment extends Fragment {

    private SkypeViewModel skypeViewModel;
    public FragmentSkypesBinding skypesBinding;

    /**
     * Обязательный конструктор класса
     */
    public SkypesFragment() { }

    /**
     * Этот метод создает класс фрагмента SkypesFragment
     * @return Новый экземпляр класса SkypesFragment
     */
    public static SkypesFragment newInstance() {
        return new SkypesFragment();
    }

    /**
     * Этот метод подготавливает активность к работе фрагмента с учетом
     * сохранённых данных
     * @param savedInstanceState Bundle - Сохранённые данные фрагмента
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if((AppCompatActivity)getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Конференции по группам");
            ViewModelProvider provider = new ViewModelProvider(this);
            skypeViewModel = provider.get(SkypeViewModel.class);
            skypeViewModel.getJsonSkype();
        }
    }

    /**
     * Этот метод создаёт фрагмент с учетом определённых
     * в {@link SkypesFragment#onCreate(Bundle)} полей
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle - Сохранённое состояние фрагмента
     * @return View - Выводит фрагмент на экран
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        skypesBinding = FragmentSkypesBinding.inflate(getLayoutInflater());

        if((AppCompatActivity)getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Конференции по группам");

            if(getActivity().getApplicationContext() != null){
                MainActivity.networkConnection.observe(getViewLifecycleOwner(), isChecked->{
                    if(isChecked){
                        skypesBinding.noInternet3.setVisibility(View.INVISIBLE);
                        skypesBinding.skypesList.setLayoutManager(new LinearLayoutManager(getContext()));
                        skypeViewModel.getSkypesMutableLiveData().observe(getViewLifecycleOwner(),
                                skypesConfs -> skypesBinding.skypesList.setAdapter(new SkypesRecyclerAdapter(skypesConfs, this)));

                        skypesBinding.confsList.setLayoutManager(new LinearLayoutManager(getContext()));
                        skypeViewModel.getConfsMutableLiveData().observe(getViewLifecycleOwner(),
                                skypesConfs -> skypesBinding.confsList.setAdapter(new SkypesRecyclerAdapter(skypesConfs ,this)));
                    } else {
                        skypesBinding.noInternet3.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

        return skypesBinding.getRoot();
    }
}
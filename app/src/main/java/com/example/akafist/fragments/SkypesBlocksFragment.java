package com.example.akafist.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.akafist.databinding.FragmentSkypesBlocksBinding;
import com.example.akafist.recyclers.SkypesGridRecyclerAdapter;
import com.example.akafist.viewmodel.SkypeViewModel;

/**
 * Класс, выводящий список ссылок на конференции
 * @author Nastya Izotina
 * @version 1.0.0
 */
public class SkypesBlocksFragment extends Fragment {

    private SkypeViewModel skypeViewModel;
    private String nameTitle;
    private int urlId;

    public FragmentSkypesBlocksBinding skypesBlocksBinding;

    /**
     * Обязательный конструктор класса
     */
    public SkypesBlocksFragment() { }

    /**
     * Этот метод создает класс фрагмента SkypesBlocksFragment
     * @return Новый экземпляр класса SkypesBlocksFragment
     */
    public static SkypesBlocksFragment newInstance() {
        return new SkypesBlocksFragment();
    }

    /**
     * Этот метод подготавливает активность к работе фрагмента с учетом
     * сохранённых данных
     * @param savedInstanceState Bundle - Сохранённые данные фрагмента
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nameTitle = getArguments().getString("nameTitle");
            urlId = getArguments().getInt("urlId");
        }
        if((AppCompatActivity)getActivity() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(nameTitle);
            ViewModelProvider provider = new ViewModelProvider(this);
            skypeViewModel = provider.get(SkypeViewModel.class);
            skypeViewModel.getJsonSkypeBlock(urlId);
        }
    }

    /**
     * Этот метод создаёт фрагмент с учетом определённых
     * в {@link SkypesBlocksFragment#onCreate(Bundle)} полей
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle - Сохранённое состояние фрагмента
     * @return View - Выводит фрагмент на экран
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        skypesBlocksBinding = FragmentSkypesBlocksBinding.inflate(inflater, container, false);

        skypesBlocksBinding.groupBlocks.setLayoutManager(new GridLayoutManager(getContext(), 2));
        skypeViewModel.getConfsMutableLiveData().observe(getViewLifecycleOwner(), view -> skypesBlocksBinding.groupBlocks.setAdapter(new SkypesGridRecyclerAdapter(view)));

        return skypesBlocksBinding.getRoot();
    }
}
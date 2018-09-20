package br.edu.ifsp.sdm.denis.layoutssdm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String ESTADO_NOTIFICACAO_CHECKBOX = "ESTADO_NOTIFICACAO_CHECKBOX";
    private final String NOTIFICACAO_RADIOBUTTON_SELECIONADO = "NOTIFICACAO_RADIOBUTTON_SELECIONADO";

    private final String TELEFONES = "TELEFONES";
    private final String EMAILS = "EMAILS";

    private CheckBox notificacoesCheckBox;
    private RadioGroup notificacoesRadioGroup;
    private EditText nomeEditText;
    private EditText emailEditText;
    private EditText telefoneEditText;

    private LinearLayout telefoneLinearLayout;
    private List<View> telefoneList;

    private LinearLayout emailLinearLayout;
    private List<View> emailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //setContentView(R.layout.relative_layout_activity_main);
        //setContentView(R.layout.linear_layout_activity_main);
        //setContentView(R.layout.grid_layout_activity_main);
        setContentView(R.layout.scrollview_layout_activity_main);

        //Referências para as views
        notificacoesCheckBox = findViewById(R.id.notificacoesCheckbox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);

        nomeEditText = findViewById(R.id.nomeEditText);
        emailEditText = findViewById(R.id.emailEditText);

        telefoneLinearLayout = findViewById(R.id.telefoneLinearLayout);
        telefoneList = new ArrayList<>();

        emailLinearLayout = findViewById(R.id.emailLinearLayout);
        emailList = new ArrayList<>();

        //Tratando evento de check no checkbox com base no click
        notificacoesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked()) {
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else {
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
            }
        });
        //Tratando evento de check no checkbox com base em mudança de estado
//        notificacoesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked) {
//                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
//                }
//                else {
//                    notificacoesRadioGroup.setVisibility(View.GONE);
//                }
//            }
//        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //salvando os telefones e e-mails
        outState.putSerializable(TELEFONES, (ArrayList<View>)telefoneList);
        outState.putSerializable(EMAILS, (ArrayList<View>)emailList);

        //salvando o estado do checkbox e botão de radio
        boolean isChecked = notificacoesCheckBox.isChecked();
        outState.putBoolean(ESTADO_NOTIFICACAO_CHECKBOX, isChecked);
        if(isChecked) {
            outState.putInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO, notificacoesRadioGroup.getCheckedRadioButtonId());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //recuperando os telefones e e-mails
        recuperarTelefones(savedInstanceState);

        recuperarEmails(savedInstanceState);

        //recuperando o estado do checkbox e botão de radio
        notificacoesCheckBox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICACAO_CHECKBOX));
        if(notificacoesCheckBox.isChecked()) {
            notificacoesRadioGroup.setVisibility(View.VISIBLE);
            int idRadioButtonSelecionado = savedInstanceState.getInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO);
            if(idRadioButtonSelecionado != -1) {
                notificacoesRadioGroup.check(idRadioButtonSelecionado);
            }
        }
        else {
            notificacoesRadioGroup.setVisibility(View.GONE);
        }

    }

    private void recuperarTelefones(Bundle savedInstanceState) {
        telefoneList = (List<View>)savedInstanceState.getSerializable(TELEFONES);
        for(View v: telefoneList) {
            ((ViewGroup)v.getParent()).removeView(v); //para desvincular a view do seu pai
            telefoneLinearLayout.addView(v);
        }
    }

    private void recuperarEmails(Bundle savedInstanceState) {
        emailList = (List<View>)savedInstanceState.getSerializable(EMAILS);
        for(View v: emailList) {
            ((ViewGroup)v.getParent()).removeView(v); //para desvincular a view do seu pai
            emailLinearLayout.addView(v);
        }
    }

    public void limparFormulario(View botao) {
        nomeEditText.setText(null);
        emailEditText.setText(null);
        telefoneEditText.setText(null);
        notificacoesRadioGroup.clearCheck();
        notificacoesCheckBox.setChecked(false);
        nomeEditText.requestFocus();
    }

    public void adicionarTelefone(View botao) {
        if(botao.getId() == R.id.adicionarTelefoneButton) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View novoTelefoneLayout = layoutInflater.inflate(R.layout.novo_telefone_layout, null);
            telefoneList.add(novoTelefoneLayout);
            telefoneLinearLayout.addView(novoTelefoneLayout);
        }
    }

    public void adicionarEmail(View botao) {
        if(botao.getId() == R.id.adicionarEmailButton) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View novoEmailLayout = layoutInflater.inflate(R.layout.novo_email_layout, null);
            emailList.add(novoEmailLayout);
            emailLinearLayout.addView(novoEmailLayout);
        }
    }
}

package com.app.doarsp;

import java.util.List;

import com.app.adapter.ListaMuralAdapter;
import com.app.model.MuralModel;
import com.app.model.UserModel;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
 
@SuppressLint("ValidFragment")
public class Principal extends Fragment {
	
	/** Tela **/
	private ImageView achivementPicture;
	private TextView nameEdit, tpSanguineo, ultimaDoacao;
	private CheckBox aptoDoar;
	private ActionBar actionBar;
	
	/** Modelo **/
	public UserModel user;
	
	//Código usuario
	int codusuario;
	
	public Principal(){}
	
	public Principal(int codUsuario){
		
	}
	
	public Principal(UserModel user){
		setUserModel(user);
	}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_principal, container, false);
        Configuracao.enableSlideMenu((DrawerLayout)getActivity().findViewById(R.id.drawer_layout), getActivity().getActionBar());
        
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Principal");
        
        // Carrega os componentes
        achivementPicture = (ImageView)rootView.findViewById(R.id.PrincipalImageView);
        nameEdit 		  = (TextView)rootView.findViewById(R.id.PrincipalNome);
        tpSanguineo 	  = (TextView)rootView.findViewById(R.id.PrincipalTIpoSanguineo);
        ultimaDoacao	  = (TextView)rootView.findViewById(R.id.PrincipalUltimaDoacao);
        aptoDoar 	      = (CheckBox)rootView.findViewById(R.id.PrincipalAptoDoar);
                
        // Seta os dados do usuário nos componentes
        
        MainActivity global = (MainActivity)getActivity();
        user = global.getUser();
        
        switch (user.getTpSanguineo()) {
        case 0:
			achivementPicture.setImageResource(R.drawable.tp_a);
			break;
        case 1:
			achivementPicture.setImageResource(R.drawable.tp_an);
			break;
        case 2:
			achivementPicture.setImageResource(R.drawable.tp_b);
			break;
        case 3:
			achivementPicture.setImageResource(R.drawable.tp_bn);
			break;
        case 4:
			achivementPicture.setImageResource(R.drawable.tp_ab);
			break;
        case 5:
			achivementPicture.setImageResource(R.drawable.tp_abn);
			break;
        case 6:
			achivementPicture.setImageResource(R.drawable.tp_o);
			break;
        case 7:
			achivementPicture.setImageResource(R.drawable.tp_on);
			break;
		default:
			break;
		}   
        
        nameEdit.setText(user.getNome());
        tpSanguineo.setText("Tipo sanguineo: " + user.getTpSanguineoAsString());
        
        if(user.getDtdUltimaDoacao().equals("null")){
        	ultimaDoacao.setText("Ainda não efetuou nenhuma doação");
        }else{
        	ultimaDoacao.setText("Data ultima doação: " + user.getDtdUltimaDoacao());	
        }
        
        aptoDoar.setChecked(user.getStatusApto());
        
        //Solicitações
        Mural mu = new Mural();
        ListView listView = (ListView)rootView.findViewById(R.id.lista_doacoes);
		List<MuralModel> mural = mu.gerarDoacaoMSG();
		final ListaMuralAdapter muralAdapter = new ListaMuralAdapter(getActivity(), mural);
		listView.setAdapter(muralAdapter);
		
		//Método para fazer funcionar o Scroll das solicitações
		listView.setOnTouchListener(new ListView.OnTouchListener() {
		        @Override
		        public boolean onTouch(View v, MotionEvent event) {
		            int action = event.getAction();
		            switch (action) {
		            case MotionEvent.ACTION_DOWN:
		                // Disallow ScrollView to intercept touch events.
		                v.getParent().requestDisallowInterceptTouchEvent(true);
		                break;

		            case MotionEvent.ACTION_UP:
		                // Allow ScrollView to intercept touch events.
		                v.getParent().requestDisallowInterceptTouchEvent(false);
		                break;
		            }

		            // Handle ListView touch events.
		            v.onTouchEvent(event);
		            return true;
		        }
		    });
        
        return rootView;
    }
    
    /** Getters and Setters **/
    
    public UserModel getUserModel() {
		return user;
	}

	public void setUserModel(UserModel user) {
		this.user = user;
	}
	
	/** Fim Getters and Setters **/
}
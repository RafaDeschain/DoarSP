package com.app.doarsp;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.adapter.ListaSolicitacoesAdapter;
import com.app.model.Solicitacoes;
import com.app.model.User;
import com.app.webservice.WebService;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
	private TextView nameEdit, tpSanguineo, ultimaDoacao, solicitacoes;
	private CheckBox aptoDoar;
	private ActionBar actionBar;
	
	/** Modelo **/
	public User user;
	
	/** Solicitacoes **/
	public List<Solicitacoes> listaSol;
	GetSolicitacao solicitacao;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		MainActivity global = (MainActivity)getActivity();
        user = global.getUser();
		solicitacao = new GetSolicitacao(user.getCodUsuario());
		solicitacao.execute();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.findItem(R.id.menuSair).setVisible(true);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.fragment_principal, container, false);
        Configuracao.enableSlideMenu((DrawerLayout)getActivity().findViewById(R.id.drawer_layout), getActivity().getActionBar());
        Configuracao.hideKeyboard(getActivity());
        
        actionBar = getActivity().getActionBar();
        actionBar.setTitle("Principal");
        
        // Carrega os componentes
        achivementPicture = (ImageView)rootView.findViewById(R.id.PrincipalImageView);
        nameEdit 		  = (TextView)rootView.findViewById(R.id.PrincipalNome);
        tpSanguineo 	  = (TextView)rootView.findViewById(R.id.PrincipalTIpoSanguineo);
        ultimaDoacao	  = (TextView)rootView.findViewById(R.id.PrincipalUltimaDoacao);
        aptoDoar 	      = (CheckBox)rootView.findViewById(R.id.PrincipalAptoDoar);
        solicitacoes	  = (TextView)rootView.findViewById(R.id.ultimasSolicitacoes);
        
        solicitacoes.setText("Carregando suas solicitações...");
                
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
        
        return rootView;
    }
    
    @Override
	public void onDestroy() {
	    super.onDestroy();
	    if (solicitacao != null) {
	    	solicitacao.cancel(true);
	    }
	}
    
    /** AsycnTask solicitação **/
    
	public class GetSolicitacao extends AsyncTask<String, Void, String>{
		
		private String[][] wsparams;
		private int userId;
		private WebService webservice;
		
		public GetSolicitacao(int userId){
			this.userId = userId;
		}
		
		@Override
		protected String doInBackground(String... params) {
			
			wsparams = new String[1][2];
			
			wsparams[0][0] = "userID";
			wsparams[0][1] = String.valueOf(userId);
			
			webservice = new WebService("solicitacoes_GetSolicitacoes", wsparams);
			return webservice.connectWS();
		}
		
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if(result != ""){
				preencherSolicitacao(result);
			}
		}
	}
	
	/** Fim AsyncTask **/
	
	public void preencherSolicitacao(String solicitacoes){

		try
		{
			listaSol = new ArrayList<Solicitacoes>();
			
			if(solicitacoes.length() > 3){
				JSONArray jsonarray = new JSONArray(solicitacoes);
				
				for(int i = 0; i < jsonarray.length(); i++){
					
					JSONObject json = jsonarray.getJSONObject(i);
					
					listaSol.add(criarSolicitacao(json.getInt("codDoacao"), 
							 json.getInt("idUserSolicitante"), 
							 json.getInt("qtnDoacoes"), 
							 json.getInt("qtnRealizadas"), 
							 json.getInt("hemoCentro"), 
							 json.getInt("tpSanguineo"), 
							 1,
							 json.getString("nomePaciente"), 
							 json.getString("dataAbertura"),
							 json.getString("comentario")));
				}
			}
			
			preencheTela(listaSol);
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	
	private void preencheTela(List<Solicitacoes> listaSol) {
		
		//Solicitações
        
        ListView listView = (ListView)getActivity().findViewById(R.id.lista_doacoes);
        
        if(!listaSol.isEmpty()){
        	solicitacoes.setText("Solicitações abertas");
            final ListaSolicitacoesAdapter hemoAdapter = new ListaSolicitacoesAdapter(getActivity(), listaSol);
            listView.setAdapter(hemoAdapter);
            listView.setSelector(R.drawable.list_selector_buscar);
        }
        else{
        	solicitacoes.setText("Sem solicitações abertas");
        	listView.setVisibility(View.INVISIBLE);
        }
		
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
	}

	public Solicitacoes criarSolicitacao(int idSolicitacao, int idUsuario,
			int quantidadeSolicitacoes, int quantidadesRealizadas,
			int idHemocentro, int tipoSanguineo, int status, String nome,
			String data, String comentario){
		
		Solicitacoes sol = new Solicitacoes(idSolicitacao, idUsuario, quantidadeSolicitacoes, 
				quantidadesRealizadas, idHemocentro, tipoSanguineo, status, nome, data, comentario);
		
		return sol;
	}
    
    /** Getters and Setters **/
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	/** Fim Getters and Setters **/
}
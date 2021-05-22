/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdw.jsf;

import java.util.Collection;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.FilterMeta;
import pdw.data.crud.CrudRole;
import pdw.data.model.Role;

/**
 *
 * @author maysharalorena
 */
@Named(value = "jsfRole")
@RequestScoped
public class JsfRole {

    /**
     * Creates a new instance of JsfRole
     */
    public JsfRole() {
    }

    private int id;
    private String nome;
    private String descricao;
    
    private List<FilterMeta> filterBy;

    @Inject
    private JsfAuth jsfAuth;

    @Inject JsfApp jsfApp;

    public String persist() {
        if(this.id != 0){
            return merge();
        }
        Role role = new Role();
        role.setName(nome);
        role.setDescription(descricao);
        role.setAdmin(jsfAuth.getUser());
        if (new pdw.data.crud.CrudRole().persist(role) == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Sucesso"));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
            return view.getViewId() + "?faces-redirect=true";

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir dados", "Error"));
        }
        return null;
    }

    public String remove(Role role) {
        if (new pdw.data.crud.CrudRole().remove(role) == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Sucesso"));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
            return view.getViewId() + "?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao excluir dados", "Error"));
        }
        return null;
    }
    
    public String merge() {
        Role role = new CrudRole().find(id);
        role.setName(nome);
        role.setDescription(descricao);
        role.setAdmin(jsfAuth.getUser());
        if (new pdw.data.crud.CrudRole().merge(role) == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Sucesso"));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            UIViewRoot view = FacesContext.getCurrentInstance().getViewRoot();
            return view.getViewId() + "?faces-redirect=true";

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao inserir dados", "Error"));
        }
        return null;
    }
    
    public String edit(Role role){
        this.nome=role.getName();
        this.descricao=role.getDescription();
        this.id = role.getId();
        return "crRoles.xhtml";
    }
   
    public Collection<Role> getAll() {
        return new pdw.data.crud.CrudRole().getAll();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}

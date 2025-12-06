package br.unimontes.ccet.dcc.pg1.controller;

import br.unimontes.ccet.dcc.pg1.model.dao.AlunoCursoDao;
import br.unimontes.ccet.dcc.pg1.model.dao.entity.AlunoCurso;
import br.unimontes.ccet.dcc.pg1.model.dao.exception.DAOException;
import java.sql.SQLException;
import javax.swing.JComboBox;

/**
 * Controller para vincular aluno a curso (matrícula)
 */
public class VincularAlunoCursoController {
    
    public boolean executa(JComboBox<String> cbAlunos, JComboBox<String> cbCursos) throws SQLException, DAOException {
        if (cbAlunos.getSelectedItem() == null || cbCursos.getSelectedItem() == null) {
            return false;
        }
        
        // Extract CPF from combo item (format: "CPF - Nome")
        String itemAluno = (String) cbAlunos.getSelectedItem();
        String cpf = itemAluno.split(" - ")[0];
        
        // Extract course ID from combo item (format: "ID - Nome")
        String itemCurso = (String) cbCursos.getSelectedItem();
        int cursoId = Integer.parseInt(itemCurso.split(" - ")[0]);
        
        AlunoCurso ac = new AlunoCurso(cpf, cursoId);
        AlunoCursoDao dao = new AlunoCursoDao();
        int res = dao.save(ac);
        return res > 0;
    }
}

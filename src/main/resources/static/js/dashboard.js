console.log('Dashboard.js carregado com sucesso!');

document.addEventListener("DOMContentLoaded", function() {
    console.log('DOMContentLoaded executado!');
    
    // Função auxiliar para definir valor de campo de forma segura
    const setFieldValue = (fieldId, value) => {
        const field = document.getElementById(fieldId);
        if (field) {
            field.value = value || '';
        }
    };
    
    // Função genérica para lidar com a lógica de modais de adição/edição
    function handleModal(modalId, formId, hiddenId, actionCallback, editCallback) {
        const modal = document.getElementById(modalId);
        if (!modal) {
            console.error('Modal não encontrado:', modalId);
            return;
        }
        
        console.log('Configurando modal:', modalId);
        
        modal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const action = button.getAttribute('data-action');
            const modalTitle = modal.querySelector('.modal-title');
            const form = document.getElementById(formId);

            if (form) {
                form.reset();
            }

            if (action === 'add') {
                modalTitle.textContent = `Adicionar Novo(a) ${modalId.replace('Modal', '')}`;
                const hiddenField = document.getElementById(hiddenId);
                if (hiddenField) {
                    hiddenField.value = '';
                }
                if (actionCallback) actionCallback(false, null);
            } else if (action === 'edit') {
                modalTitle.textContent = `Editar ${modalId.replace('Modal', '')}`;
                const row = button.closest('tr');
                if (editCallback) editCallback(row);
                if (actionCallback) actionCallback(true, row);
            }
        });
    }

    // Lógica para o modal de Moto
    handleModal('motoModal', 'motoForm', 'motoId', (isEdit, row) => {
        console.log('Modal moto - ação:', isEdit ? 'editar' : 'adicionar');
    }, (row) => {
        console.log('Editando moto, dados da linha:', row);
        
        // Lógica de edição para Moto
        const motoId = row.getAttribute('data-moto-id');
        const placa = row.getAttribute('data-moto-placa');
        const chassi = row.getAttribute('data-moto-chassi');
        const qrCode = row.getAttribute('data-moto-qrcode');
        const previsaoEntrega = row.getAttribute('data-moto-previsao-entrega');
        const observacoes = row.getAttribute('data-moto-observacoes');
        const zonaId = row.getAttribute('data-moto-zona-id');
        const patioId = row.getAttribute('data-moto-patio-id');
        const statusId = row.getAttribute('data-moto-status-id');

        // Preenchimento dos campos
        setFieldValue('motoId', motoId);
        setFieldValue('placa', placa);
        setFieldValue('chassi', chassi);
        setFieldValue('qrCode', qrCode);
        setFieldValue('observacoes', observacoes);
        setFieldValue('zonaId', zonaId);
        setFieldValue('patioId', patioId);
        setFieldValue('statusId', statusId);

        // Tratamento especial para data (apenas data, sem horário)
        if (previsaoEntrega && previsaoEntrega !== 'null') {
            try {
                const date = new Date(previsaoEntrega);
                if (!isNaN(date.getTime())) {
                    const formattedDate = date.toISOString().slice(0, 10); // Apenas YYYY-MM-DD
                    setFieldValue('previsaoEntrega', formattedDate);
                }
            } catch (e) {
                console.warn('Erro ao formatar data:', e);
            }
        }
        
        console.log('Dados preenchidos no formulário de moto:', {
            motoId, placa, chassi, qrCode, previsaoEntrega, zonaId, patioId, statusId
        });
    });

    // Lógica para o modal de Pátio
    handleModal('patioModal', 'patioForm', 'patioId', (isEdit, row) => {
        console.log('Modal patio - ação:', isEdit ? 'editar' : 'adicionar');
    }, (row) => {
        console.log('Editando patio, dados da linha:', row);
        setFieldValue('patioId', row.getAttribute('data-patio-id'));
        setFieldValue('patioNome', row.getAttribute('data-patio-nome'));
    });

    // Lógica para o modal de Zona
    handleModal('zonaModal', 'zonaForm', 'zonaId', (isEdit, row) => {
        console.log('Modal zona - ação:', isEdit ? 'editar' : 'adicionar');
    }, (row) => {
        console.log('Editando zona, dados da linha:', row);
        setFieldValue('zonaId', row.getAttribute('data-zona-id'));
        setFieldValue('zonaNome', row.getAttribute('data-zona-nome'));
        setFieldValue('zonaLetra', row.getAttribute('data-zona-letra'));
    });

    // Lógica para o modal de Status
    handleModal('statusModal', 'statusForm', 'statusId', (isEdit, row) => {
        console.log('Modal status - ação:', isEdit ? 'editar' : 'adicionar');
    }, (row) => {
        console.log('Editando status, dados da linha:', row);
        setFieldValue('statusId', row.getAttribute('data-status-id'));
        setFieldValue('statusNome', row.getAttribute('data-status-nome'));
        setFieldValue('statusGrupoId', row.getAttribute('data-status-grupo-id'));
    });

    // Lógica para o modal de Grupo de Status
    handleModal('statusGrupoModal', 'statusGrupoForm', 'statusGrupoId', (isEdit, row) => {
        console.log('Modal status grupo - ação:', isEdit ? 'editar' : 'adicionar');
    }, (row) => {
        console.log('Editando status grupo, dados da linha:', row);
        setFieldValue('statusGrupoId', row.getAttribute('data-grupo-id'));
        setFieldValue('statusGrupoNome', row.getAttribute('data-grupo-nome'));
    });

    // Confirmação para os botões de exclusão
    const deleteButtons = document.querySelectorAll('.delete-btn');
    console.log('Botões de exclusão encontrados:', deleteButtons.length);
    
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(event) {
            if (!confirm('Tem certeza que deseja excluir este item?')) {
                event.preventDefault();
            }
        });
    });

    // Validação customizada e feedback visual para formulários
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            // Validação específica para o formulário de moto
            if (form.id === 'motoForm') {
                const placa = document.getElementById('placa').value.trim();
                const chassi = document.getElementById('chassi').value.trim();
                const qrCode = document.getElementById('qrCode').value.trim();
                
                if (!placa && !chassi && !qrCode) {
                    event.preventDefault();
                    alert('Preencha pelo menos um dos campos: Placa, Chassi ou QR Code');
                    return;
                }
                
                console.log('Enviando formulário de moto com dados:', {
                    id: document.getElementById('motoId').value,
                    placa, chassi, qrCode
                });
            }
            
            const submitBtn = form.querySelector('button[type="submit"]');
            if (submitBtn) {
                submitBtn.innerHTML = '<span class="spinner-border spinner-border-sm me-2"></span>Salvando...';
                submitBtn.disabled = true;
            }
        });
    });
});
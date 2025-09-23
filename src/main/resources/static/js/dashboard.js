console.log('Dashboard.js carregado com sucesso!');

document.addEventListener("DOMContentLoaded", function() {
    console.log('DOMContentLoaded executado!');
    
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

            form.reset();

            if (action === 'add') {
                modalTitle.textContent = `Adicionar Novo(a) ${modalId.replace('Modal', '')}`;
                document.getElementById(hiddenId).value = '';
                actionCallback(false, null);
            } else if (action === 'edit') {
                modalTitle.textContent = `Editar ${modalId.replace('Modal', '')}`;
                const row = button.closest('tr');
                editCallback(row);
                actionCallback(true, row);
            }
        });
    }

    // Lógica para o modal de Moto
    handleModal('motoModal', 'motoForm', 'motoId', (isEdit, row) => {
        // Nenhuma ação específica de adição necessária aqui, pois o reset do formulário já é suficiente.
    }, (row) => {
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

        document.getElementById('motoId').value = motoId;
        document.getElementById('placa').value = placa;
        document.getElementById('chassi').value = chassi;
        document.getElementById('qrCode').value = qrCode;
        document.getElementById('previsaoEntrega').value = previsaoEntrega ? new Date(previsaoEntrega).toISOString().slice(0, 16) : '';
        document.getElementById('observacoes').value = observacoes;
        document.getElementById('zonaId').value = zonaId;
        document.getElementById('patioId').value = patioId;
        document.getElementById('statusId').value = statusId;
    });

    // Lógica para o modal de Pátio
    handleModal('patioModal', 'patioForm', 'patioId', (isEdit, row) => {
        // Ações de adição/edição
    }, (row) => {
        // Lógica de edição para Pátio
        document.getElementById('patioId').value = row.getAttribute('data-patio-id');
        document.getElementById('patioNome').value = row.getAttribute('data-patio-nome');
    });

    // Lógica para o modal de Zona
    handleModal('zonaModal', 'zonaForm', 'zonaId', (isEdit, row) => {
        // Ações de adição/edição
    }, (row) => {
        // Lógica de edição para Zona
        document.getElementById('zonaId').value = row.getAttribute('data-zona-id');
        document.getElementById('zonaNome').value = row.getAttribute('data-zona-nome');
        document.getElementById('zonaLetra').value = row.getAttribute('data-zona-letra');
    });

    // Lógica para o modal de Status
    handleModal('statusModal', 'statusForm', 'statusId', (isEdit, row) => {
        // Ações de adição/edição
    }, (row) => {
        // Lógica de edição para Status
        document.getElementById('statusId').value = row.getAttribute('data-status-id');
        document.getElementById('statusNome').value = row.getAttribute('data-status-nome');
        document.getElementById('statusGrupoId').value = row.getAttribute('data-status-grupo-id');
    });

    // Lógica para o modal de Grupo de Status
    handleModal('statusGrupoModal', 'statusGrupoForm', 'statusGrupoId', (isEdit, row) => {
        // Ações de adição/edição
    }, (row) => {
        // Lógica de edição para Grupo de Status
        document.getElementById('statusGrupoId').value = row.getAttribute('data-grupo-id');
        document.getElementById('statusGrupoNome').value = row.getAttribute('data-grupo-nome');
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
});
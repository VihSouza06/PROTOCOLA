
const opcoes = {
    credentials: 'include',
    headers: { 'Content-Type': 'application/json' }
};

async function verificarSessao() {
    const res = await fetch('/api/auth/me', { credentials: 'include' });
    if (!res.ok) { window.location.href = '/login.html'; return null; }
    return await res.json();
}

async function verificarRole(roleNecessario) {
    const u = await verificarSessao();
    if (!u) return null;
    if (u.role !== roleNecessario) {
        alert('Acesso negado.');
        window.location.href = '/login.html';
        return null;
    }
    return u;
}

async function login(loginStr, password) {
    const res = await fetch('/api/auth/login', {
        ...opcoes, method: 'POST',
        body: JSON.stringify({ login: loginStr, password })
    });
    return { ok: res.ok, data: await res.json() };
}

async function logout() {
    await fetch('/api/auth/logout', { ...opcoes, method: 'POST' });
    window.location.href = '/login.html';
}

async function registro(login, password, role) {
    const res = await fetch('/api/auth/registro', {
        ...opcoes, method: 'POST',
        body: JSON.stringify({ login, password, role })
    });
    return { ok: res.ok, data: await res.json() };
}

async function criarSolicitacao(dados) {
    const res = await fetch('/api/solicitacoes', {
        ...opcoes, method: 'POST',
        body: JSON.stringify(dados)
    });
    return { ok: res.ok, data: await res.json() };
}

async function buscarPorProtocolo(protocolo) {
    const res = await fetch(`/api/solicitacoes/protocolo/${protocolo}`, { credentials: 'include' });
    return { ok: res.ok, data: await res.json() };
}

async function listarSolicitacoes(filtros = {}) {
    const params = new URLSearchParams();
    if (filtros.prioridade) params.append('prioridade', filtros.prioridade);
    if (filtros.categoria)  params.append('categoria',  filtros.categoria);
    if (filtros.endereco)   params.append('endereco',   filtros.endereco);
    const res = await fetch(`/api/solicitacoes${params.toString() ? '?' + params : ''}`,
        { credentials: 'include' });
    return { ok: res.ok, data: await res.json() };
}

async function atualizarStatus(protocolo, dados) {
    const res = await fetch(`/api/solicitacoes/${protocolo}/status`, {
        ...opcoes, method: 'PATCH',
        body: JSON.stringify(dados)
    });
    return { ok: res.ok, data: await res.json() };
}

function mostrarAlerta(id, msg, tipo = 'erro') {
    const el = document.getElementById(id);
    if (!el) return;
    el.className = `alerta alerta-${tipo} visivel`;
    el.textContent = msg;
}
function esconderAlerta(id) {
    const el = document.getElementById(id);
    if (el) el.className = 'alerta';
}
function badgeStatus(s) {
    return `<span class="badge badge-${s}">${s.replace('_', ' ')}</span>`;
}
function badgePrioridade(p) {
    return `<span class="badge badge-${p}">${p}</span>`;
}
function formatarData(iso) {
    if (!iso) return '—';
    return new Date(iso).toLocaleString('pt-BR');
}

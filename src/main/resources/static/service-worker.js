const CACHE_NAME = 'rutaya-v1';

// Archivos que se guardan en caché para funcionar sin internet
const ARCHIVOS_CACHE = [
  '/',
  '/index.html',
  '/admin.html',
  '/terminos.html',
  '/privacidad.html',
  '/logo.png',
  '/hero-bg.png',
  '/manifest.json',
  'https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Sora:wght@700;800;900&display=swap'
];

// ─── INSTALACIÓN: guarda archivos en caché ───
self.addEventListener('install', event => {
  console.log('[Rutaya SW] Instalando service worker...');
  event.waitUntil(
    caches.open(CACHE_NAME).then(cache => {
      console.log('[Rutaya SW] Guardando archivos en caché');
      return cache.addAll(ARCHIVOS_CACHE);
    }).then(() => self.skipWaiting())
  );
});

// ─── ACTIVACIÓN: limpia cachés viejas ───
self.addEventListener('activate', event => {
  console.log('[Rutaya SW] Activado');
  event.waitUntil(
    caches.keys().then(keys =>
      Promise.all(
        keys.filter(key => key !== CACHE_NAME).map(key => {
          console.log('[Rutaya SW] Eliminando caché vieja:', key);
          return caches.delete(key);
        })
      )
    ).then(() => self.clients.claim())
  );
});

// ─── FETCH: estrategia Network First para API, Cache First para estáticos ───
self.addEventListener('fetch', event => {
  const url = new URL(event.request.url);

  // Para llamadas a la API siempre usar la red (datos en tiempo real)
  if (url.pathname.startsWith('/api/')) {
    event.respondWith(
      fetch(event.request).catch(() =>
        new Response(
          JSON.stringify({ ok: false, mensaje: 'Sin conexión a internet. Por favor conecta tu red e intenta de nuevo.' }),
          { headers: { 'Content-Type': 'application/json' } }
        )
      )
    );
    return;
  }

  // Para archivos estáticos: primero caché, si no hay va a la red
  event.respondWith(
    caches.match(event.request).then(cached => {
      if (cached) return cached;

      return fetch(event.request).then(response => {
        // Solo cachear respuestas válidas
        if (!response || response.status !== 200 || response.type === 'opaque') {
          return response;
        }
        const copia = response.clone();
        caches.open(CACHE_NAME).then(cache => cache.put(event.request, copia));
        return response;
      }).catch(() => {
        // Si no hay red y no hay caché, mostrar página offline básica
        if (event.request.destination === 'document') {
          return caches.match('/index.html');
        }
      });
    })
  );
});

// ─── PUSH: para notificaciones futuras ───
self.addEventListener('push', event => {
  const datos = event.data ? event.data.json() : { titulo: 'Rutaya', cuerpo: 'Tienes una notificación' };
  event.waitUntil(
    self.registration.showNotification(datos.titulo || 'Rutaya', {
      body: datos.cuerpo || '',
      icon: '/logo.png',
      badge: '/logo.png',
      vibrate: [100, 50, 100]
    })
  );
});

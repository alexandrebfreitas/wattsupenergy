import { Authority } from '@/shared/security/authority';
const Entities = () => import('@/entities/entities.vue');

const Acomph = () => import('@/entities/acomph/acomph.vue');
const AcomphUpdate = () => import('@/entities/acomph/acomph-update.vue');
const AcomphDetails = () => import('@/entities/acomph/acomph-details.vue');

const PostoMedicao = () => import('@/entities/posto-medicao/posto-medicao.vue');
const PostoMedicaoUpdate = () => import('@/entities/posto-medicao/posto-medicao-update.vue');
const PostoMedicaoDetails = () => import('@/entities/posto-medicao/posto-medicao-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'acomph',
      name: 'Acomph',
      component: Acomph,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'acomph/new',
      name: 'AcomphCreate',
      component: AcomphUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'acomph/:acomphId/edit',
      name: 'AcomphEdit',
      component: AcomphUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'acomph/:acomphId/view',
      name: 'AcomphView',
      component: AcomphDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'posto-medicao',
      name: 'PostoMedicao',
      component: PostoMedicao,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'posto-medicao/new',
      name: 'PostoMedicaoCreate',
      component: PostoMedicaoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'posto-medicao/:postoMedicaoId/edit',
      name: 'PostoMedicaoEdit',
      component: PostoMedicaoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'posto-medicao/:postoMedicaoId/view',
      name: 'PostoMedicaoView',
      component: PostoMedicaoDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};

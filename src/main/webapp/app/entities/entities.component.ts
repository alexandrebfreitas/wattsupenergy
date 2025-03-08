import { defineComponent, provide } from 'vue';

import AcomphService from './acomph/acomph.service';
import PostoMedicaoService from './posto-medicao/posto-medicao.service';
import UserService from '@/entities/user/user.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

export default defineComponent({
  compatConfig: { MODE: 3 },
  name: 'Entities',
  setup() {
    provide('userService', () => new UserService());
    provide('acomphService', () => new AcomphService());
    provide('postoMedicaoService', () => new PostoMedicaoService());
    // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
  },
});

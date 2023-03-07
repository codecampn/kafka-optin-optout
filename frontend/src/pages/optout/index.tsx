import { Container } from '@/components/Container';
import { Card } from '@/components/Card';
import { OptOutEmail } from '@/components/opt-out/OptOutEmail';
import { OptOutPhone } from '@/components/opt-out/OptOutPhone';

export default function OptOutPage() {
  return (
    <Container className="flew-row flex space-x-8">
      <Card headline="OptOut E-mail" className="mt-8">
        <OptOutEmail />
      </Card>
      <Card headline="OptOut Phone" className="mt-8">
        <OptOutPhone />
      </Card>
    </Container>
  );
}

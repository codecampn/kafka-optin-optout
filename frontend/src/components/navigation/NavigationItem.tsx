import classnames from 'classnames';
import Link from 'next/link';
import { Route } from './Navigation';

export const NavItem = ({ href, name, pathname }: Route & { pathname: string }) => {
  return (
    <li className="flex items-center text-center">
      <Link
        href={href}
        className={classnames(
          pathname === href && 'border-b-2 ',
          'font-medium text-white hover:border-b-2'
        )}
      >
        {name}
      </Link>
    </li>
  );
};

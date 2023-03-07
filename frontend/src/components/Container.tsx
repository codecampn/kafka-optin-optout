import classNames from 'classnames';

interface ContainerProps {
  as?: 'div' | 'nav';
  className?: string;
  children: React.ReactNode;
}

export const Container = ({
  as: HTMLElement = 'div',
  className = undefined,
  children,
}: ContainerProps) => (
  <HTMLElement
    className={classNames(
      'mx-auto max-w-screen-xl',
      'px-3 xl:px-0 ',
      className
    )}
  >
    {children}
  </HTMLElement>
);
